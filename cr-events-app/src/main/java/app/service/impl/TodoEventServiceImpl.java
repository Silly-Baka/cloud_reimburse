package app.service.impl;

import app.event.entity.TodoEvent;
import app.mapper.TodoEventMapper;
import app.service.TodoEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import static app.constants.TodoEventState.*;


public class TodoEventServiceImpl extends ServiceImpl<TodoEventMapper, TodoEvent> implements TodoEventService {
    @Override
    public int getUnhandledEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .ne("state", HANDLED)
                .count();
    }

    @Override
    public int getSupervisedEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .eq("state", SUPERVISED)
                .count();
    }

    @Override
    public int getOutOfTimeEventsNum(Long userId) {
        return this.query()
                .eq("todo_user", userId)
                .ne("state", OUT_OF_TIME)
                .count();
    }
}
