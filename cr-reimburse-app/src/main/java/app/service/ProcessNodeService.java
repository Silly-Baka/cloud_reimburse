package app.service;

import app.reimburse.entity.ProcessNode;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:48
 *
 * @Author SillyBaka
 **/
@Service
public interface ProcessNodeService extends IService<ProcessNode> {

    /**
     * 生成报销流程图
     *
     * @param userId  报销发起者id
     * @param sheetId 报销单id
     * @return 当前流程节点id
     */
    Long generateReimburseProcessMap(Long userId, Long sheetId) throws Exception;
}
