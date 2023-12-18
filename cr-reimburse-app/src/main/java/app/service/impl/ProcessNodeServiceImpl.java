package app.service.impl;

import app.api.UserApi;
import app.dto.UserDTO;
import app.dto.UserQryDTO;
import app.entity.ProcessNode;
import app.mapper.ProcessNodeMapper;
import app.service.ProcessNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：流程节点-服务层实现类
 * <p>Date: 2023/12/18
 * <p>Time: 20:48
 *
 * @Author SillyBaka
 **/
public class ProcessNodeServiceImpl extends ServiceImpl<ProcessNodeMapper, ProcessNode> implements ProcessNodeService {

    @Resource
    private UserApi userApi;

    @Override
    @Transactional(rollbackFor = {})  //TODO：补充rollbackFor
    public void generateReimburseProcessMap(Long userId, Long sheetId) throws Exception {
        // 报销发起 --> 部门报销管控人员审批 --> 部门领导审批 --> 财务处理 --> 报销完成

        // 1、查找发起者所在部门名
        UserQryDTO qryDTO = new UserQryDTO();
        qryDTO.setId(userId);
        List<UserDTO> result = userApi.getUserSelective(qryDTO).getData();
        // 找不到发起者相关信息
        if(result == null || result.size() == 0) {
            //TODO：补充自定义异常类
            throw new Exception();
        }
        String deptName = result.get(0).getDeptName();

        // 生成节点-报销发起

        // 生成节点-部门报销管控人员审批

        // 生成节点-部门领导审批

        // 生成节点-财务处理

        // 生成节点-报销完成

    }
}
