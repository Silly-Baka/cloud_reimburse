package app.contoller;

import app.common.CommonResult;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.ReimburseSheetQryDTO;
import app.service.ReimburseService;
import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description：报销单相关controller
 * <p>Date: 2023/12/18
 * <p>Time: 18:41
 *
 * @Author SillyBaka
 **/
@RestController("/reimburse")
public class ReimburseController {

    @Resource
    private ReimburseService reimburseService;

    @ApiOperation("日常费用报销申请")
    @PostMapping("/applyDaily")
    public CommonResult applyDaily(@RequestParam("dailyReimburseReqDTO") DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception {
        //TODO：补充检查逻辑
        if(!dailyReimburseReqDTO.checkParam()) {
            return CommonResult.fail(400, "请求参数错误");
        }
        return CommonResult.ok(reimburseService.applyDaily(dailyReimburseReqDTO));
    }

    @ApiOperation("流程节点流转")
    @PostMapping("/process/change")
    public CommonResult processChange(@RequestParam("processNodeId") Long processNodeId, @RequestParam("userId") Long userId,
                                      @RequestParam("feedBack") String feedBack) {
        if(processNodeId == null || userId == null) {
            return CommonResult.fail(400, "请求参数不能为空");
        }
        return CommonResult.ok(reimburseService.processChange(processNodeId, userId, feedBack));
    }

    @ApiOperation("获取指定用户的报销单列表")
    @GetMapping("/listById")
    public CommonResult getReimburseList(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return CommonResult.fail(400, "用户id不能为空");
        }
        return CommonResult.ok(reimburseService.getReimburseList(userId));
    }

    @ApiOperation("条件查询报销单列表")
    @GetMapping("/listSelective")
    public CommonResult getReimburseListSelective(@RequestParam("qryDTO") ReimburseSheetQryDTO qryDTO) {
        if(qryDTO == null) {
            return CommonResult.fail(400, "请求参数不可为空");
        }
        return CommonResult.ok(reimburseService.getReimburseListSelective(qryDTO));
    }
}
