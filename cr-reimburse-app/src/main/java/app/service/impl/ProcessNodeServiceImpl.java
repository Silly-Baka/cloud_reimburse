package app.service.impl;

import app.api.UserApi;
import app.constants.CommonState;
import app.constants.ProcessNodeType;
import app.constants.RoleName;
import app.user.dto.UserDTO;
import app.user.dto.UserQryDTO;
import app.reimburse.entity.ProcessNode;
import app.mapper.ProcessNodeMapper;
import app.service.ProcessNodeService;
import app.utils.IdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProcessNodeServiceImpl extends ServiceImpl<ProcessNodeMapper, ProcessNode> implements ProcessNodeService {

    @Resource
    private UserApi userApi;

    private final Random random = new Random();

    @Override
    @Transactional
    public ProcessNode generateReimburseProcessMap(Long userId, Long sheetId) throws Exception {
        // 报销发起 --> 部门报销管控人员审批 --> 部门领导审批 --> 财务处理 --> 报销完成

        // 1、查找发起者所在部门名
        UserDTO result = userApi.getUserById(userId).getData();
        // 找不到发起者相关信息
        if(result == null) {
            //TODO：补充自定义异常类
            throw new Exception();
        }
        String deptName = result.getDeptName();

        int order = 0;
        List<ProcessNode> processNodeList = new ArrayList<>();

        // 生成节点-报销发起
        ProcessNode applyNode = new ProcessNode();
        applyNode.setId(IdGenerator.getUniqueId(ProcessNode.class));
        applyNode.setSheetId(sheetId);
        applyNode.setOrder(order++);
        applyNode.setOprUser(userId);
        applyNode.setState(CommonState.PASS.getVal());
        applyNode.setType(ProcessNodeType.APPLY);
        processNodeList.add(applyNode);

        // 生成节点-部门报销管控人员审批
        List<Long> reimburseController = userApi.getUserByDeptAndRole(deptName, RoleName.REIMBURSE_CONTROLLER).getData();
        if(reimburseController == null || reimburseController.size() == 0) {
            //TODO：补充自定义异常类
            throw new Exception();
        }
        ProcessNode reimburseNode = new ProcessNode();
        reimburseNode.setId(IdGenerator.getUniqueId(ProcessNode.class));
        reimburseNode.setSheetId(sheetId);
        reimburseNode.setOrder(order++);
        // 设置成部门内随机一个报销管控人员
        reimburseNode.setOprUser(reimburseController.get(random.nextInt(reimburseController.size())));
        reimburseNode.setState(CommonState.CONTINUE.getVal());
        reimburseNode.setType(ProcessNodeType.APPROVAL);
        processNodeList.add(reimburseNode);

        // 生成节点-部门领导审批
        List<Long> deptManagers = userApi.getUserByDeptAndRole(deptName, RoleName.DEPT_MANAGER).getData();
        if(deptManagers == null || deptManagers.size() == 0) {
            //TODO：补充自定义异常类
            throw new Exception();
        }
        ProcessNode deptNode = new ProcessNode();
        deptNode.setId(IdGenerator.getUniqueId(ProcessNode.class));
        deptNode.setSheetId(sheetId);
        deptNode.setOrder(order++);
        // 设置成部门内随机一个领导
        deptNode.setOprUser(deptManagers.get(random.nextInt(deptManagers.size())));
        deptNode.setState(CommonState.CONTINUE.getVal());
        deptNode.setType(ProcessNodeType.APPROVAL);
        processNodeList.add(deptNode);

        // 生成节点-财务处理
        List<Long> financeStaffs = userApi.getUserByDeptAndRole(deptName, RoleName.FINANCE_STAFF).getData();
        if(financeStaffs == null || financeStaffs.size() == 0) {
            //TODO：补充自定义异常类
            throw new Exception();
        }
        ProcessNode financeNode = new ProcessNode();
        financeNode.setId(IdGenerator.getUniqueId(ProcessNode.class));
        financeNode.setSheetId(sheetId);
        financeNode.setOrder(order++);
        // 设置成随机一个财务
        financeNode.setOprUser(financeStaffs.get(random.nextInt(financeStaffs.size())));
        financeNode.setState(CommonState.CONTINUE.getVal());
        financeNode.setType(ProcessNodeType.PAY);
        processNodeList.add(financeNode);

        // 生成节点-报销完成
        ProcessNode finishNode = new ProcessNode();
        finishNode.setId(IdGenerator.getUniqueId(ProcessNode.class));
        finishNode.setSheetId(sheetId);
        finishNode.setOrder(order);
        finishNode.setState(CommonState.CONTINUE.getVal());
        finishNode.setType(ProcessNodeType.FINISH);
        finishNode.setLast(1);
        finishNode.setOprUser(userId);
        processNodeList.add(finishNode);

        for (ProcessNode processNode : processNodeList) {
            this.save(processNode);
        }

        // 返回当前流程节点的id
        return reimburseNode;
    }
}
