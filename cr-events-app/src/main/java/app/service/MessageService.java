package app.service;

import app.event.dto.UpdateToReadReqDTO;
import app.event.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Description：
 * <p>Date: 2024/3/4
 * <p>Time: 23:37
 *
 * @Author SillyBaka
 **/
public interface MessageService extends IService<Message> {

    List<Message> getSystemNotice(Long userId);

    List<Message> getEventNotice(Long userId);

    Boolean updateToRead(UpdateToReadReqDTO reqDTO);
}
