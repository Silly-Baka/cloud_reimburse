package app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Description：待办事件消费者服务，消费mq中的EventMessage
 * <p>Date: 2023/12/28
 * <p>Time: 19:10
 *
 * @Author SillyBaka
 **/
@Service
public class EventConsumerService {

    @Value("${kafka.topic.event-topic}")
    private String eventTopic;


}
