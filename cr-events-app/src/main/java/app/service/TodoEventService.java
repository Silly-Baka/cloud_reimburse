package app.service;

import app.common.CommonResult;
import app.event.dto.TodoEventQryDTO;
import app.event.dto.TodoEventResultDTO;
import app.event.entity.TodoEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface TodoEventService extends IService<TodoEvent> {

    /**
     * 查询目标用户尚未处理完毕的事件数
     * @param userId 目标用户id
     * @return 尚未处理完毕的事件数
     */
    int getUnhandledEventsNum(Long userId);

    /**
     * 查询目标用户被督办的事件数
     * @param userId 目标用户id
     * @return 被督办的事件数
     */
    int getSupervisedEventsNum(Long userId);

    /**
     * 查询目标用户已超时的事件数
     * @param userId 目标用户id
     * @return 已超时的事件数
     */
    int getOutOfTimeEventsNum(Long userId);

    /**
     * 条件查询待办事件列表
     * @param qryDTO
     * @return
     */
    Page<TodoEventResultDTO> listTodoEvents(@RequestBody TodoEventQryDTO qryDTO);

    /**
     * 将指定待办事件设置为已处理状态
     * @param sheetId 报销单id
     * @param todoUser 执行者id
     * @return
     */
    Boolean doneTodoEvent(Long sheetId, Long todoUser);

    List<Long> getToPayList(Long userId);
}
