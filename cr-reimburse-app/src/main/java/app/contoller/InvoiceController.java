package app.contoller;

import app.common.CommonResult;
import app.reimburse.dto.InvoiceAddDTO;
import app.reimburse.dto.InvoiceUpdateDTO;
import app.reimburse.entity.Invoice;
import app.reimburse.entity.InvoiceInfo;
import app.service.InvoiceService;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Description：发票相关的controller
 * <p>Date: 2023/12/18
 * <p>Time: 18:40
 *
 * @Author SillyBaka
 **/
@RestController
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    @ApiOperation("上传发票")
    @PostMapping("/invoice/upload")
    public CommonResult upload(@RequestBody InvoiceAddDTO invoiceAddDTO) {
        //DONE：补充逻辑
        if(invoiceAddDTO == null) {
            return CommonResult.fail(400, "输入参数不能为空");
        }
        return CommonResult.ok(invoiceService.upload(invoiceAddDTO));
    }

    @ApiOperation("查询个人所有发票概要信息")
    @GetMapping("/invoice/getOwnInvoice")
    public CommonResult getOwnInvoice(@RequestParam("/ownerId") Long ownerId) {
        //DONE：补充逻辑
        if(ownerId == null) {
            return CommonResult.fail(400, "用户id不能为空");
        }
        return CommonResult.ok(invoiceService.getOwnInvoice(ownerId));
    }

    @ApiOperation("查询指定发票信息")
    @GetMapping("/invoice/{invoiceId}")
    public CommonResult getInvoice(@PathVariable("invoiceId") Long invoiceId) {
        //DONE：补充逻辑
        if(invoiceId == null) {
            return CommonResult.fail(400, "发票id不能为空");
        }
        return CommonResult.ok(invoiceService.getById(invoiceId));
    }

    @ApiOperation("更新发票信息")
    @PutMapping("/invoice/update")
    public CommonResult update(@RequestBody InvoiceUpdateDTO invoiceUpdateDTO) {
        if(invoiceUpdateDTO == null) {
            return CommonResult.fail(400, "输入参数不能为空");
        }
        return CommonResult.ok(invoiceService.updateInvoice(invoiceUpdateDTO));
    }

    @ApiOperation("上传发票文件（在发票信息已上传后调用）")
    @PostMapping("/invoice/uploadInvoiceFile")
    public CommonResult uploadInvoiceFile(@RequestParam("file") MultipartFile file, @RequestParam("invoiceId") Long invoiceId) throws IOException {
        if(file == null) {
            return CommonResult.fail(400, "上传的发票文件不能为空");
        }
        return CommonResult.ok(invoiceService.uploadInvoiceFile(file, invoiceId));
    }
}
