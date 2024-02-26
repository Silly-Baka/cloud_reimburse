package app.controller;

import app.common.CommonResult;
import app.event.dto.DoneEventReqDTO;
import app.event.dto.TodoEventQryDTO;
import app.service.TodoEventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：负责待办事件的controller
 * <p>Date: 2023/12/27
 * <p>Time: 21:21
 *
 * @Author SillyBaka
 **/
@RestController
public class TodoEventController {

    @Resource
    private TodoEventService todoEventService;

    @PostMapping("/todo/list")
    @ApiOperation("获取当前用户的待办事件列表")
    public CommonResult listTodoEvents(@RequestBody TodoEventQryDTO qryDTO) {
        if(qryDTO.getPageNum() == null || qryDTO.getPageNum() == 0) {
            qryDTO.setPageNum(1);
        }
        if(qryDTO.getPageSize() == null || qryDTO.getPageSize() == 0) {
            qryDTO.setPageSize(20);
        }
        return CommonResult.ok(todoEventService.listTodoEvents(qryDTO));
    }

    @PostMapping("/todo/done")
    @ApiOperation("将指定待办事件设置为已处理状态")
    public CommonResult doneTodoEvent(@RequestBody DoneEventReqDTO doneEventReqDTO) {
        if(doneEventReqDTO.getSheetId() == null || doneEventReqDTO.getTodoUser() == null) {
            return CommonResult.fail(400, "请求参数不可为空");
        }
        return CommonResult.ok(todoEventService.doneTodoEvent(doneEventReqDTO.getSheetId(), doneEventReqDTO.getTodoUser()));
    }

    @ApiOperation("获取指定用户需要支付的报销单id列表")
    @GetMapping("/todo/pay/list")
    public  CommonResult getToPayList(@RequestParam Long userId) {
        if(userId == null) {
            return CommonResult.fail(400, "请求参数不可为空");
        }
        return CommonResult.ok(todoEventService.getToPayList(userId));
    }
}
