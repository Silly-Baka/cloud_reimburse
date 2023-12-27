package app.service.impl;

import app.event.entity.TodoEvent;
import app.mapper.TodoEventMapper;
import app.service.TodoEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/27
 * <p>Time: 21:18
 *
 * @Author SillyBaka
 **/
@Service
public class TodoEventServiceImpl extends ServiceImpl<TodoEventMapper, TodoEvent> implements TodoEventService {
}
