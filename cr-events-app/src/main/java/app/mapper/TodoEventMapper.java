package app.mapper;

import app.event.entity.TodoEvent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description：
 * <p>Date: 2023/12/27
 * <p>Time: 21:18
 *
 * @Author SillyBaka
 **/
@Mapper
public interface TodoEventMapper extends BaseMapper<TodoEvent> {
}
