package app.api;

import app.CommonResult;
import app.event.dto.DoneEventReqDTO;
import app.event.dto.TodoEventQryDTO;
import app.event.dto.TodoEventResultDTO;
import app.event.dto.UpdateToReadReqDTO;
import app.event.entity.Message;
import app.event.entity.TodoEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：待办事件api接口
 * <p>Date: 2024/1/28
 * <p>Time: 18:13
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-todoEvent", url = "${api.url.api-todoEvent}")
public interface TodoEventApi {

    @PostMapping("/todo/list")
    @ApiOperation("获取当前用户的待办事件列表")
    CommonResult<Page<TodoEventResultDTO>> listTodoEvents(@RequestBody TodoEventQryDTO qryDTO);

    @PostMapping("/todo/done")
    @ApiOperation("将指定待办事件设置为已处理状态")
    CommonResult<Boolean> doneTodoEvent(@RequestBody DoneEventReqDTO doneEventReqDTO);

    @ApiOperation("获取指定用户需要支付的报销单id列表")
    @GetMapping("/todo/pay/list")
    CommonResult<List<Long>> getToPayList(@RequestParam("userId") Long userId);

    @ApiOperation("获取系统通知类站内信")
    @GetMapping("/message/systemNotice")
    CommonResult<List<Message>> getSystemNotice(@RequestParam("userId") Long userId);

    @ApiOperation("获取事件提醒类站内信")
    @GetMapping("/message/eventNotice")
    CommonResult<List<Message>> getEventNotice(@RequestParam("userId") Long userId);


    @ApiOperation("将指定站内信列表更新为已读状态")
    @PostMapping("/message/read")
    CommonResult updateToRead(@RequestBody UpdateToReadReqDTO reqDTO);
}
