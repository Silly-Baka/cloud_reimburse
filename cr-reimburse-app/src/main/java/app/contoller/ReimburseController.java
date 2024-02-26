package app.contoller;

import app.common.CommonResult;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.FinancePayReqDTO;
import app.reimburse.dto.ProcessChangeReqDTO;
import app.reimburse.dto.ReimburseSheetQryDTO;
import app.service.ReimburseService;
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
@RestController
public class ReimburseController {

    @Resource
    private ReimburseService reimburseService;

    @ApiOperation("日常费用报销申请")
    @PostMapping("/reimburse/applyDaily")
    public CommonResult applyDaily(@RequestBody DailyReimburseReqDTO dailyReimburseReqDTO) throws Exception {
        //TODO：补充检查逻辑
        if(!dailyReimburseReqDTO.checkParam()) {
            return CommonResult.fail(400, "请求参数错误");
        }
        return CommonResult.ok(reimburseService.applyDaily(dailyReimburseReqDTO));
    }

    @ApiOperation("流程节点流转")
    @PostMapping("/reimburse/process/change")
    public CommonResult processChange(@RequestBody  ProcessChangeReqDTO processChangeReqDTO) {
        Long processNodeId = processChangeReqDTO.getProcessNodeId();
        Long userId = processChangeReqDTO.getUserId();
        String feedBack = processChangeReqDTO.getFeedBack();
        if(processNodeId == null || userId == null) {
            return CommonResult.fail(400, "请求参数不能为空");
        }
        return CommonResult.ok(reimburseService.processChange(processNodeId, userId, feedBack));
    }

    @ApiOperation("获取指定用户的报销单列表")
    @GetMapping("/reimburse/listById")
    public CommonResult getReimburseList(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return CommonResult.fail(400, "用户id不能为空");
        }
        return CommonResult.ok(reimburseService.getReimburseList(userId));
    }

    @ApiOperation("条件查询报销单列表")
    @PostMapping("/reimburse/listSelective")
    public CommonResult getReimburseListSelective(@RequestBody ReimburseSheetQryDTO qryDTO) {
        if(qryDTO == null) {
            return CommonResult.fail(400, "请求参数不可为空");
        }
        return CommonResult.ok(reimburseService.getReimburseListSelective(qryDTO));
    }

    @ApiOperation("根据id获取日常报销单详细信息")
    @GetMapping("/reimburse/daily/info")
    public CommonResult getDailyReimburseInfo(@RequestParam("sheetId") Long sheetId) {
        if(sheetId == null) {
            return CommonResult.fail(400, "报销单id不可为空");
        }
        return CommonResult.ok(reimburseService.getDailyReimburseInfo(sheetId));
    }

    @ApiOperation("查询指定报销单的所有流程节点（用于生成流程图）")
    @GetMapping("/reimburse/process/list")
    public CommonResult getReimburseProcessNodeList(@RequestParam("sheetId") Long sheetId) {
        if(sheetId == null) {
            return CommonResult.fail(400, "报销单id不可为空");
        }
        return CommonResult.ok(reimburseService.getReimburseProcessNodeList(sheetId));
    }

    @ApiOperation("根据id获取报销单的基本信息（通用信息）")
    @GetMapping("/reimburse/sheet/base")
    public CommonResult getReimburseSheetBase(@RequestParam("sheetId") Long sheetId) {
        if(sheetId == null) {
            return CommonResult.fail(400, "报销单id不可为空");
        }
        return CommonResult.ok(reimburseService.getReimburseSheetBase(sheetId));
    }

    @ApiOperation("为指定报销单列表进行支付")
    @PostMapping("/finance/pay")
    public CommonResult financePay(@RequestBody FinancePayReqDTO reqDTO) {
        if(reqDTO.getReimburseSheetIdList() == null || reqDTO.getReimburseSheetIdList().size() == 0) {
            return CommonResult.fail(400, "支付条目不可为空");
        }
        return CommonResult.ok(reimburseService.financePay(reqDTO));
    }

    @ApiOperation("获取指定用户需支付的报销单列表")
    @GetMapping("/finance/toPay/list")
    public CommonResult getToPaySheetList(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return CommonResult.fail(401, "用户id不可为空");
        }
        return CommonResult.ok(reimburseService.getToPaySheetList(userId));
    }

    @ApiOperation("根据id获取指定报销单的报销金额")
    @GetMapping("/reimburse/sheet/price")
    public CommonResult getReimburseSheetPrice(@RequestParam("sheetId") Long sheetId) {
        if(sheetId == null) {
            return CommonResult.fail(400, "报销单id不可为空");
        }
        return CommonResult.ok(reimburseService.getReimburseSheetPrice(sheetId));
    }
}
