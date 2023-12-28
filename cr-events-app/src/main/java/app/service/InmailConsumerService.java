package app.service;

import app.api.UserApi;
import app.constants.CommonState;
import app.event.entity.Message;
import app.event.kafka.InmailMessage;
import app.mapper.MessageMapper;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;
import app.utils.IdGenerator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Date;

import static app.constants.InmailContentFormat.PROCESS_CHANGE_FORMAT;
import static app.constants.InmailType.*;
import static app.constants.ProcessNodeType.NODE_TYPE_NAMES;

/**
 * Description：站内信消费者服务，消费mq中的InmailMessage
 * <p>Date: 2023/12/28
 * <p>Time: 19:09
 *
 * @Author SillyBaka
 **/
@Service
public class InmailConsumerService {

    @Value("${kafka.topic.inmail-topic}")
    private String inmailTopic;
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserApi userApi;


    private final InmailContentFormatter contentFormatter = new InmailContentFormatter();


    @KafkaListener(topics = {"${kafka.topic.inmail-topic}"})
    public void consumeInmailMessage(ConsumerRecord<String, InmailMessage> record) {
        InmailMessage inmailMessage = record.value();

        // 入库站内信
        Message message = new Message();
        message.setId(IdGenerator.getUniqueId(Message.class));
        message.setMsgType(inmailMessage.getInmailType());
        message.setFrom(0L);   //TODO：目前此属性默认为0
        message.setState(CommonState.CONTINUE.getVal());
        message.setCreateTime(new Date(record.timestamp()));

        String content = "";
        switch (inmailMessage.getInmailType()) {
            case PROCESS_CHANGE:

                ReimburseSheet reimburseSheet = inmailMessage.getReimburseSheet();
                ProcessNode processNode = inmailMessage.getProcessNode();
                message.setTo(reimburseSheet.getApplicantId());
                content = contentFormatter.processChangeContent(reimburseSheet, processNode);
                break;

            case NEW_EVENT:

                //TODO：待补充逻辑
                break;

            case PROCESS_SUPERVISE:

                break;

            case TIMEOUT_EVENT:

                break;

            default:
                break;
        }


    }

    /**
     * 站内信内容格式化器
     */
    private class InmailContentFormatter {

        /**
         * 流程流转-信息内容
         */
        public String processChangeContent(ReimburseSheet reimburseSheet, ProcessNode processNode) {
            String sheetName = reimburseSheet.getName();
            String nodeTypeName = NODE_TYPE_NAMES.get(processNode.getType());

            // 获取操作者姓名
            Long oprUserId = processNode.getOprUser();
            String oprUserName = userApi.getUserById(oprUserId).getData().getUsername();

            return String.format(PROCESS_CHANGE_FORMAT, sheetName, nodeTypeName, oprUserName);
        }

        /**
         * 新事件-信息内容
         * @return
         */
        public String newEventContent() {
            //TODO：待补充逻辑
            return null;
        }

    }
}
