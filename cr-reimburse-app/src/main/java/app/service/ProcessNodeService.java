package app.service;

import app.entity.ProcessNode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:48
 *
 * @Author SillyBaka
 **/
public interface ProcessNodeService extends IService<ProcessNode> {

    /**
     * 生成报销流程图
     * @param userId 报销发起者id
     * @param sheetId 报销单id
     */
    void generateReimburseProcessMap(Long userId, Long sheetId) throws Exception;
}
