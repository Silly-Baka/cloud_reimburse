package app.service.impl;

import app.event.entity.TodoEvent;
import app.mapper.TodoEventMapper;
import app.service.TodoEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


public class TodoEventServiceImpl extends ServiceImpl<TodoEventMapper, TodoEvent> implements TodoEventService {
}
