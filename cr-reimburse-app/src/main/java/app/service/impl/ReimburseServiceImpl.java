package app.service.impl;

import app.constants.CommonState;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.DailySheetInfoReqDTO;
import app.reimburse.entity.DailySheetInfo;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;
import app.mapper.DailySheetInfoMapper;
import app.mapper.ReimburseSheetMapper;
import app.service.ProcessNodeService;
import app.service.ReimburseService;
import app.utils.IdGenerator;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


public class ReimburseServiceImpl extends ServiceImpl<ReimburseSheetMapper, ReimburseSheet> implements ReimburseService {

    @Resource
    private ProcessNodeService processNodeService;

    @Resource
    private DailySheetInfoMapper dailySheetInfoMapper;

    @Override
    @Transactional
    public Long applyDaily(DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception {
        //DONE：补充逻辑

        Long sheetId = IdGenerator.getUniqueId(ReimburseSheet.class);

        //1、生成流程节点
        Long curNodeId = processNodeService.generateReimburseProcessMap(dailyReimburseReqDTO.getApplicantId(), sheetId);

        //2、插入报销单表
        ReimburseSheet reimburseSheet = new ReimburseSheet();
        BeanUtil.copyProperties(dailyReimburseReqDTO, reimburseSheet);
        reimburseSheet.setId(sheetId);
        reimburseSheet.setCurNodeId(curNodeId);
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
                .orderByDesc("order")
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

        //TODO：发起站内信提醒【发起流程的用户】   -->  交给mq，
        //TODO：发起待办事件提醒【下一节点的用户】   -->  交给mq


        return true;
    }
}
