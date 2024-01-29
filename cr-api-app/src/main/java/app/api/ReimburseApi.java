package app.api;

import app.CommonResult;
import app.reimburse.dto.*;
import app.reimburse.entity.ReimburseSheet;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    CommonResult upload(@RequestBody InvoiceAddDTO invoice);

    @ApiOperation("查询个人所有发票")
    @GetMapping("/invoice/getOwnInvoice")
    CommonResult<List<InvoiceResultDTO>> getOwnInvoice(@RequestParam("ownerId") Long ownerId);

    @ApiOperation("查询指定发票信息")
    @GetMapping("/invoice/{invoiceId}")
    CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId);

    @ApiOperation("更新发票信息")
    @PutMapping("/invoice/update")
    CommonResult update(@RequestBody InvoiceAddDTO invoice);

    @ApiOperation("上传发票文件（在发票信息已上传后调用）")
    @PostMapping(value="/invoice/uploadInvoiceFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult uploadInvoiceFile(@RequestPart("file") MultipartFile file, @RequestParam("invoiceId") Long invoiceId);

    @ApiOperation("删除指定发票列表")
    @PostMapping("/invoice/delete/list")
    CommonResult deleteInvoiceList(@RequestBody List<Long> invoiceIds);

    @ApiOperation("条件查询发票列表")
    @PostMapping("/invoice/list/selective")
    CommonResult<List<InvoiceResultDTO>> getInvoiceListSelective(@RequestBody InvoiceQryDTO qryDTO);


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

    @ApiOperation("查询指定报销单的所有流程节点（用于生成流程图）")
    @GetMapping("/reimburse/process/list")
    CommonResult getReimburseProcessNodeList(@RequestParam("sheetId") Long sheetId);

    @ApiOperation("根据id获取报销单的基本信息（通用信息）")
    @GetMapping("/reimburse/sheet/base")
    CommonResult<ReimburseSheet> getReimburseSheetBase(@RequestParam("sheetId") Long sheetId);
}
