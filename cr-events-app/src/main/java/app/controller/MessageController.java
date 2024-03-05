package app.controller;

import app.common.CommonResult;
import app.event.dto.UpdateToReadReqDTO;
import app.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description： 站内信controller
 * <p>Date: 2024/3/4
 * <p>Time: 23:32
 *
 * @Author SillyBaka
 **/
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    @ApiOperation("获取系统通知类站内信")
    @GetMapping("/message/systemNotice")
    public CommonResult getSystemNotice(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return CommonResult.fail(400, "用户尚未登录，无法获取");
        }
        return CommonResult.ok(messageService.getSystemNotice(userId));
    }

    @ApiOperation("获取事件提醒类站内信")
    @GetMapping("/message/eventNotice")
    public CommonResult getEventNotice(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return CommonResult.fail(400, "用户尚未登录，无法获取");
        }
        return CommonResult.ok(messageService.getEventNotice(userId));
    }

    @ApiOperation("将指定站内信列表更新为已读状态")
    @PostMapping("/message/read")
    public CommonResult updateToRead(@RequestBody UpdateToReadReqDTO reqDTO) {
        if(reqDTO == null || reqDTO.getMessages() == null) {
            return CommonResult.fail(400, "错误请求，传入的站内信列表为空");
        }
        return CommonResult.ok(messageService.updateToRead(reqDTO));
    }
}
