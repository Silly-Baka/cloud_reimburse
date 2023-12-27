package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * Description：对接kafka的业务实现类
 * <p>Date: 2023/12/28
 * <p>Time: 0:01
 *
 * @Author SillyBaka
 **/
@Service
public class KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.message-topic}")
    private String messageTopic;

    @Value("${kafka.topic.event-topic}")
    private String eventTopic;

    /**
     * 发送消息给对应topic
     * @param topic
     * @param message
     */
    public void sendMessage(String topic, Object message) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, null, System.currentTimeMillis(), String.valueOf(message.hashCode()), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOGGER.info("生产者成功发送消息到 [" + topic + "] --> " + result.getProducerRecord().value().toString());
            }
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("生产者发送消息：{} 失败，错误原因：{}", message, ex.getMessage());
            }
        });
    }



}
