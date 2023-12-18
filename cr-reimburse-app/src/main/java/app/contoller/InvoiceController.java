package app.contoller;

import app.common.CommonResult;
import app.dto.InvoiceDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Description：发票相关的controller
 * <p>Date: 2023/12/18
 * <p>Time: 18:40
 *
 * @Author SillyBaka
 **/
@RestController("/invoice")
public class InvoiceController {

    @ApiOperation("上传发票")
    @PostMapping("/upload")
    public CommonResult upload(@RequestParam("invoice") InvoiceDTO invoice) {
        //TODO：补充逻辑
        return null;
    }

    @ApiOperation("查询个人所有发票概要信息")
    @GetMapping("/getOwnInvoice")
    public CommonResult getOwnInvoice(@RequestParam("/ownerId") Long ownerId) {
        //TODO：补充逻辑
        return null;
    }

    @ApiOperation("查询指定发票信息")
    @GetMapping("/{invoiceId}")
    public CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId) {
        //TODO：补充逻辑
        return null;
    }

    @ApiOperation("更新发票信息")
    @PutMapping("/update")
    public CommonResult update(@RequestParam("invoice") InvoiceDTO invoice) {
        //TODO：补充逻辑
        return null;
    }

}
