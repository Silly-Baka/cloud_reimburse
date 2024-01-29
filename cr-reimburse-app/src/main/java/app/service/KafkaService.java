package app.service;

import app.event.kafka.EventMessage;
import app.event.kafka.InmailMessage;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;
import app.utils.IdGenerator;
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

    @Value("${kafka.topic.inmail-topic}")
    private String inmailTopic;

    @Value("${kafka.topic.event-topic}")
    private String eventTopic;

    /**
     * 发送消息给对应的topic
     * @param topic
     * @param message
     */
    private void sendMessage(String topic, Object message) {
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

    /**
     * 发起站内信通知报销单的发起者
     * @param reimburseSheet 报销单信息
     * @param curNode 当前流程节点
     */
    public void sendInmailMessage(ReimburseSheet reimburseSheet, ProcessNode curNode) {
        InmailMessage inmailMessage = new InmailMessage();
        inmailMessage.setId(IdGenerator.getUniqueId(InmailMessage.class));
        inmailMessage.setInmailType(1);
        inmailMessage.setReimburseSheet(reimburseSheet);
        inmailMessage.setProcessNode(curNode);

        sendMessage(inmailTopic, inmailMessage);
    }

    /**
     * 发起事件通知目标节点的处理者
     * @param reimburseSheet 目标报销单信息
     * @param processNode 目标节点
     */
    public void sendEventMessage(ReimburseSheet reimburseSheet,ProcessNode processNode) {
        EventMessage eventMessage = new EventMessage();
        eventMessage.setId(IdGenerator.getUniqueId(EventMessage.class));
        eventMessage.setProcessNode(processNode);
        eventMessage.setReimburseSheet(reimburseSheet);

        sendMessage(eventTopic, eventMessage);
    }


}
