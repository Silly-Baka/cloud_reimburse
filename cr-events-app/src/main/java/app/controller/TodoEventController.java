package app.controller;

import app.common.CommonResult;
import app.event.dto.TodoEventQryDTO;
import app.service.TodoEventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
