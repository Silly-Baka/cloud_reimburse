package app.api;

import app.dto.CommonResult;
import app.dto.DailyReimburseDTO;
import app.dto.InvoiceDTO;
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
    @PostMapping("/upload")
    CommonResult upload(@RequestParam("invoice") InvoiceDTO invoice);

    @ApiOperation("查询个人所有发票")
    @GetMapping("/getOwnInvoice")
    CommonResult getOwnInvoice(@RequestParam("/ownerId") Long ownerId);

    @ApiOperation("查询指定发票信息")
    @GetMapping("/{invoiceId}")
    CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId);

    @ApiOperation("更新发票信息")
    @PutMapping("/update")
    CommonResult update(@RequestParam("invoice") InvoiceDTO invoice);


    // ----------  报销单服务  -------------

    @ApiOperation("日常费用报销申请")
    @PostMapping("/applyDaily")
    CommonResult applyDaily(@RequestParam("dailyReimburseDTO") DailyReimburseDTO dailyReimburseDTO);

}
