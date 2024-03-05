package app.event.dto;

import app.event.entity.Message;

import java.util.List;

/**
 * Description：
 * <p>Date: 2024/3/5
 * <p>Time: 15:30
 *
 * @Author SillyBaka
 **/
public class UpdateToReadReqDTO {

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
