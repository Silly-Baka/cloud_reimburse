package app.controller;

import app.CommonResult;
import app.api.ReimburseApi;
import app.reimburse.dto.DailyReimburseReqDTO;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.ReimburseSheetQryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description：api网关，负责报销模块接口
 * <p>Date: 2024/1/2
 * <p>Time: 22:10
 *
 * @Author SillyBaka
 **/
@RestController
public class ReimburseApiController {

    @Resource
    private ReimburseApi reimburseApi;

    @ApiOperation("上传发票")
    @PostMapping("/api/v1/invoice/upload")
    CommonResult upload(@RequestParam("invoice") InvoiceAddDTO invoice) {
        return reimburseApi.upload(invoice);
    }

    @ApiOperation("查询个人所有发票")
    @GetMapping("/api/v1/invoice/getOwnInvoice")
    CommonResult getOwnInvoice(@RequestParam("/ownerId") Long ownerId) {
        return reimburseApi.getOwnInvoice(ownerId);
    }

    @ApiOperation("查询指定发票信息")
    @GetMapping("/api/v1/invoice/{invoiceId}")
    CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId) {
        return reimburseApi.getInvoice(invoiceId);
    }

    @ApiOperation("更新发票信息")
    @PutMapping("/api/v1/invoice/update")
    CommonResult update(@RequestParam("invoice") InvoiceAddDTO invoice) {
        return reimburseApi.update(invoice);
    }


    // ----------  报销单服务  -------------

    @ApiOperation("日常费用报销申请")
    @PostMapping("/api/v1/reimburse/applyDaily")
    CommonResult applyDaily(@RequestBody DailyReimburseReqDTO dailyReimburseReqDTO) {
        return reimburseApi.applyDaily(dailyReimburseReqDTO);
    }

    @ApiOperation("流程节点流转")
    @PostMapping("/api/v1/reimburse/process/change")
    CommonResult processChange(@RequestParam("processNodeId") Long processNodeId, @RequestParam("userId") Long userId) {
        return reimburseApi.processChange(processNodeId, userId);
    }

    @ApiOperation("获取指定用户的报销单列表")
    @GetMapping("/api/v1/reimburse/listById")
    CommonResult getReimburseList(@RequestParam("userId") Long userId) {
        return reimburseApi.getReimburseList(userId);
    }

    @ApiOperation("条件查询报销单列表")
    @GetMapping("/api/v1/reimburse/listSelective")
    CommonResult getReimburseListSelective(ReimburseSheetQryDTO qryDTO) {
        return reimburseApi.getReimburseListSelective(qryDTO);
    }

    @ApiOperation("根据id获取报销单详细信息")
    @GetMapping("/api/v1/reimburse/daily/info")
    public CommonResult getDailyReimburseInfo(@RequestParam("sheetId") Long sheetId) {
        return reimburseApi.getDailyReimburseInfo(sheetId);
    }

    @ApiOperation("查询指定报销单的所有流程节点（用于生成流程图）")
    @GetMapping("/api/v1/reimburse/process/list")
    CommonResult getReimburseProcessNodeList(@RequestParam("sheetId") Long sheetId) {
        return reimburseApi.getReimburseProcessNodeList(sheetId);
    }
}
