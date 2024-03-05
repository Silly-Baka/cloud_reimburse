package app.service.impl;

import app.event.dto.UpdateToReadReqDTO;
import app.event.entity.Message;
import app.mapper.MessageMapper;
import app.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description：
 * <p>Date: 2024/3/4
 * <p>Time: 23:38
 *
 * @Author SillyBaka
 **/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    @Transactional
    public List<Message> getSystemNotice(Long userId) {

        List<Message> messageList = this.query()
                .eq("`to`", userId)
                .eq("msg_type", 1)
                .eq("state", 0)
                .list();

//        // 获取后修改状态，标识为已读
//        for (Message message : messageList) {
//            message.setState(1);
//            this.updateById(message);
//        }

        return messageList;
    }

    @Override
    public List<Message> getEventNotice(Long userId) {
        List<Message> messageList = this.query()
                .eq("`to`", userId)
                .eq("msg_type", 2)
                .eq("state", 0)
                .list();

//        // 获取后修改状态，标识为已读
//        for (Message message : messageList) {
//            message.setState(1);
//            this.updateById(message);
//        }

        return messageList;
    }

    @Override
    @Transactional
    public Boolean updateToRead(UpdateToReadReqDTO reqDTO) {
        List<Message> messages = reqDTO.getMessages();
        for (Message message : messages) {
            message.setState(1);
            this.updateById(message);
        }
        return true;
    }
}
