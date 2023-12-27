package app.service;

import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.entity.ReimburseSheet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 * <p>Date: 2023/12/18
 * <p>Time: 20:40
 *
 * @Author SillyBaka
 **/
@Service
public interface ReimburseService extends IService<ReimburseSheet> {

    /**
     * 发起日常报销
     * @param dailyReimburseReqDTO
     * @return 报销单id
     */
    Long applyDaily(@RequestParam("dailyReimburseReqDTO") DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception;


    /**
     * 流程节点流转
     *
     * @param processNodeId 当前节点id
     * @param userId        当前用户id
     * @param feedBack      流转反馈
     * @return true-流转成功， false-流转失败
     */
    Boolean processChange(Long processNodeId, Long userId, String feedBack);

}
