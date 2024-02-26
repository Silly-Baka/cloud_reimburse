package app.controller;

import app.CommonResult;
import app.api.TodoEventApi;
import app.event.dto.DoneEventReqDTO;
import app.event.dto.TodoEventQryDTO;
import app.event.dto.TodoEventResultDTO;
import app.event.entity.TodoEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description：
 * <p>Date: 2024/1/28
 * <p>Time: 18:15
 *
 * @Author SillyBaka
 **/
@RestController
public class TodoEventApiController {

    @Resource
    private TodoEventApi todoEventApi;

    @PostMapping("/api/v1/todo/list")
    @ApiOperation("获取当前用户的待办事件列表")
    public CommonResult<Page<TodoEventResultDTO>> listTodoEvents(@RequestBody TodoEventQryDTO qryDTO) {
        return todoEventApi.listTodoEvents(qryDTO);
    }

    @PostMapping("/api/v1/todo/done")
    @ApiOperation("将指定待办事件设置为已处理状态")
    public CommonResult doneTodoEvent(@RequestBody DoneEventReqDTO doneEventReqDTO)  {
        return todoEventApi.doneTodoEvent(doneEventReqDTO);
    }
}
