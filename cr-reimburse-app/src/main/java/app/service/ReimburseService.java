package app.service;

import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.DailyReimburseResultDTO;
import app.reimburse.dto.ProcessNodeDTO;
import app.reimburse.dto.ReimburseSheetQryDTO;
import app.reimburse.entity.ReimburseSheet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


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

    /**
     * 获取指定用户的报销单列表
     * @param userId 用户id
     * @return 报销单列表
     */
    List<ReimburseSheet> getReimburseList(Long userId);


    /**
     * 条件查询报销单列表
     * @param qryDTO 条件
     * @return 报销单列表
     */
    List<ReimburseSheet> getReimburseListSelective(ReimburseSheetQryDTO qryDTO);

    /**
     * 获取日常报销单详细信息
     *
     * @param sheetId 报销单id
     * @return
     */
    DailyReimburseResultDTO getDailyReimburseInfo(Long sheetId);

    /**
     * 获取指定报销单的流程节点列表
     *
     * @param sheetId 报销单id
     * @return 流程节点列表
     */
    List<ProcessNodeDTO> getReimburseProcessNodeList(Long sheetId);
}
