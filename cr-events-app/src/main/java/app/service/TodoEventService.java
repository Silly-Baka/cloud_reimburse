package app.service;

import app.event.entity.TodoEvent;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * Description：
 * <p>Date: 2023/12/27
 * <p>Time: 21:18
 *
 * @Author SillyBaka
 **/
@Service
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
}
