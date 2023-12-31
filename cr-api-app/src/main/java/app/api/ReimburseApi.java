package app.api;

import app.CommonResult;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.ReimburseSheetQryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Description：报销模块api
 * <p>Date: 2023/12/18
 * <p>Time: 18:42
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-reimburse", url = "${api.url.api-reimburse}")
public interface ReimburseApi {

    // ----------  发票服务  -------------

    @ApiOperation("上传发票")
    @PostMapping("/invoice/upload")
    CommonResult upload(@RequestParam("invoice") InvoiceAddDTO invoice);

    @ApiOperation("查询个人所有发票")
    @GetMapping("/invoice/getOwnInvoice")
    CommonResult getOwnInvoice(@RequestParam("/ownerId") Long ownerId);

    @ApiOperation("查询指定发票信息")
    @GetMapping("/invoice/{invoiceId}")
    CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId);

    @ApiOperation("更新发票信息")
    @PutMapping("/invoice/update")
    CommonResult update(@RequestParam("invoice") InvoiceAddDTO invoice);


    // ----------  报销单服务  -------------

    @ApiOperation("日常费用报销申请")
    @PostMapping("/reimburse/applyDaily")
    CommonResult applyDaily(@RequestBody DailyReimburseReqDTO dailyReimburseReqDTO);

    @ApiOperation("流程节点流转")
    @PostMapping("/reimburse/process/change")
    CommonResult processChange(@RequestParam("processNodeId") Long processNodeId, @RequestParam("userId") Long userId);

    @ApiOperation("获取指定用户的报销单列表")
    @GetMapping("/reimburse/listById")
    CommonResult getReimburseList(@RequestParam("userId") Long userId);

    @ApiOperation("条件查询报销单列表")
    @PostMapping("/reimburse/listSelective")
    CommonResult getReimburseListSelective(@RequestBody ReimburseSheetQryDTO qryDTO);

    @ApiOperation("根据id获取日常报销单详细信息")
    @GetMapping("/reimburse/daily/info")
    CommonResult getDailyReimburseInfo(@RequestParam("sheetId") Long sheetId);
}
