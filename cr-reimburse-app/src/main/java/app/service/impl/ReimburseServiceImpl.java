package app.service.impl;

import app.api.UserApi;
import app.constants.CommonState;
import app.constants.ProcessNodeType;
import app.reimburse.dto.*;
import app.reimburse.entity.DailySheetInfo;
import app.reimburse.entity.Invoice;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;
import app.mapper.DailySheetInfoMapper;
import app.mapper.ReimburseSheetMapper;
import app.service.InvoiceService;
import app.service.KafkaService;
import app.service.ProcessNodeService;
import app.service.ReimburseService;
import app.user.dto.UserDTO;
import app.utils.IdGenerator;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReimburseServiceImpl extends ServiceImpl<ReimburseSheetMapper, ReimburseSheet> implements ReimburseService {

    @Resource
    private ProcessNodeService processNodeService;
    @Resource
    private ReimburseSheetMapper reimburseSheetMapper;
    @Resource
    private DailySheetInfoMapper dailySheetInfoMapper;
    @Resource
    private KafkaService kafkaService;
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private UserApi userApi;

    @Override
    @Transactional
    public Long applyDaily(DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception {
        //DONE：补充逻辑

        Long sheetId = IdGenerator.getUniqueId(ReimburseSheet.class);

        //1、生成流程节点
        ProcessNode curNode = processNodeService.generateReimburseProcessMap(dailyReimburseReqDTO.getApplicantId(), sheetId);

        //2、插入报销单表
        ReimburseSheet reimburseSheet = new ReimburseSheet();
        BeanUtil.copyProperties(dailyReimburseReqDTO, reimburseSheet);
        reimburseSheet.setId(sheetId);
        reimburseSheet.setCurNodeId(curNode.getId());
        reimburseSheet.setState(CommonState.CONTINUE.getVal());
        this.save(reimburseSheet);

        //3、插入日常报销信息表
        for (DailySheetInfoReqDTO sheetInfoReqDTO : dailyReimburseReqDTO.getDailySheetInfoReqDTOList()) {
            DailySheetInfo dailySheetInfo = new DailySheetInfo();
            BeanUtil.copyProperties(sheetInfoReqDTO, dailySheetInfo);

            dailySheetInfo.setId(IdGenerator.getUniqueId(DailySheetInfo.class));
            dailySheetInfo.setSheetId(sheetId);
            dailySheetInfoMapper.insert(dailySheetInfo);
        }

        // 修改相关发票的sheet_id信息
        List<Long> invoiceIds = new ArrayList<>();
        for (InvoiceResultDTO invoiceResultDTO : dailyReimburseReqDTO.getRelevantInvoiceList()) {
            invoiceIds.add(invoiceResultDTO.getId());
        }
        invoiceService.updateInvoiceListSheetId(invoiceIds, sheetId);

        // 4、向当前节点处理者发起待办事件
        kafkaService.sendEventMessage(reimburseSheet, curNode);

        //4、返回报销单id
        return sheetId ;
    }

    @Override
    @Transactional
    public Boolean processChange(Long processNodeId, Long userId, String feedBack) {
        // 查找当前节点信息
        ProcessNode curNode = processNodeService.getById(processNodeId);
        if(curNode == null) {
            return false;
        }
        // 操作者不正确 或 流程已失败、通过，流转失败
        if(!userId.equals(curNode.getOprUser()) || curNode.getState() != CommonState.CONTINUE.getVal()) {
            return false;
        }
        // 查找所在流程线中，已通过的流程节点中的最大序号（检查前一个节点是否已通过）
        ProcessNode latestNode = processNodeService.query()
                .eq("sheet_id", curNode.getSheetId())
                .eq("state", CommonState.PASS.getVal())
                .orderByDesc("`order`")
                .last("limit 1")
                .one();
        Integer curOrder = curNode.getOrder();
        if(latestNode == null || curOrder-1 != latestNode.getOrder()) {
            return false;
        }
        // 更新当前节点状态，流转到下一个节点
        curNode.setState(CommonState.PASS.getVal());
        curNode.setFeedback(feedBack);
        processNodeService.updateById(curNode);

        //DONE：发起站内信提醒【发起流程的用户】   -->  交给mq，
        ReimburseSheet reimburseSheet = reimburseSheetMapper.selectById(curNode.getSheetId());
        kafkaService.sendInmailMessage(reimburseSheet, curNode);

        if(curNode.isLast() == 1) {
            return true;
        }
        //DONE：发起待办事件提醒【下一节点的用户】   -->  交给mq
        ProcessNode nextNode = processNodeService.query()
                .eq("sheet_id", curNode.getSheetId())
                .eq("`order`", curOrder + 1)
                .one();

        // 将报销单curNodeId修改
        this.update()
                .eq("id", reimburseSheet.getId())
                .set("cur_node_id", nextNode.getId())
                .update();

        kafkaService.sendEventMessage(reimburseSheet, nextNode);

        return true;
    }

    @Override
    public List<ReimburseSheet> getReimburseList(Long userId) {
        return this.query()
                .eq("applicant_id", userId)
                .list();
    }

    @Override
    public List<ReimburseSheet> getReimburseListSelective(ReimburseSheetQryDTO qryDTO) {
        QueryChainWrapper<ReimburseSheet> queryChainWrapper = this.query();
        if(qryDTO.getApplicantId() != null) {
            queryChainWrapper.eq("applicant_id", qryDTO.getApplicantId());
        }
        if(qryDTO.getStartDate() != null && qryDTO.getEndDate() != null) {
            queryChainWrapper.between("create_time", qryDTO.getStartDate(), qryDTO.getEndDate());
        }
        if(qryDTO.getType() != null) {
            queryChainWrapper.eq("type", qryDTO.getType());
        }
        if(qryDTO.getState() != null){
            queryChainWrapper.eq("state", qryDTO.getState());
        }
        return queryChainWrapper.list();
    }

    @Override
    public DailyReimburseResultDTO getDailyReimburseInfo(Long sheetId) {

        // 基础信息
        ReimburseSheet reimburseSheet = this.query()
                .eq("id", sheetId)
                .one();

        // 费用事项信息
        List<DailySheetInfo> dailySheetInfoList = new QueryChainWrapper<>(dailySheetInfoMapper)
                .eq("sheet_id", sheetId)
                .list();

        DailyReimburseResultDTO result = BeanUtil.copyProperties(reimburseSheet, DailyReimburseResultDTO.class);
        result.setDailySheetInfoList(dailySheetInfoList);

        String applicantName = userApi.getUserById(reimburseSheet.getApplicantId()).getData().getRealName();
        result.setApplicantName(applicantName);

        ProcessNode processNode = processNodeService.getById(reimburseSheet.getCurNodeId());
        result.setCurNodeType(ProcessNodeType.NODE_TYPE_NAMES.get(processNode.getType()));
        result.setCurNodeOprUser(processNode.getOprUser());

        // 查询相关发票
        List<InvoiceResultDTO> relevantInvoiceList = invoiceService.getInvoiceListBySheetId(sheetId);
        result.setRelevantInvoiceList(relevantInvoiceList);

        return result;
    }

    @Override
    public List<ProcessNodeDTO> getReimburseProcessNodeList(Long sheetId) {

        List<ProcessNode> processNodeList = processNodeService.query()
                .eq("sheet_id", sheetId)
                .list();
        // 根据排序升序排序
        processNodeList.sort((n1, n2) -> {
            return n1.getOrder()-n2.getOrder();
        });

        List<ProcessNodeDTO> result = new ArrayList<>();

        for (ProcessNode processNode : processNodeList) {
            ProcessNodeDTO processNodeDTO = BeanUtil.copyProperties(processNode, ProcessNodeDTO.class);

            UserDTO userDTO = userApi.getUserById(processNodeDTO.getOprUser()).getData();
            processNodeDTO.setOprUserName(userDTO.getRealName());
            result.add(processNodeDTO);
        }

        return result;
    }

    @Override
    public ReimburseSheet getReimburseSheetBase(Long sheetId) {
        return this.query()
                .eq("id", sheetId)
                .one();
    }
}
