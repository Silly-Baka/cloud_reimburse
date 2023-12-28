package app.mapper;

import app.event.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description：
 * <p>Date: 2023/12/28
 * <p>Time: 20:46
 *
 * @Author SillyBaka
 **/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
