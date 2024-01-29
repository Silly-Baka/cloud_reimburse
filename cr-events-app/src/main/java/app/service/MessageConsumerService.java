package app.service;

import app.api.UserApi;
import app.constants.CommonState;
import app.constants.ProcessNodeType;
import app.constants.TodoEventType;
import app.event.entity.Message;
import app.event.entity.TodoEvent;
import app.event.kafka.EventMessage;
import app.event.kafka.InmailMessage;
import app.mapper.MessageMapper;
import app.mapper.TodoEventMapper;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;
import app.utils.IdGenerator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.record.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Date;
import java.util.Calendar;

import static app.constants.InmailContentFormat.NEW_EVENT_FORMAT;
import static app.constants.InmailContentFormat.PROCESS_CHANGE_FORMAT;
import static app.constants.InmailType.*;
import static app.constants.ProcessNodeType.NODE_TYPE_NAMES;

/**
 * Description：消费者服务，消费mq中的信息
 * <p>Date: 2023/12/28
 * <p>Time: 19:09
 *
 * @Author SillyBaka
 **/
@Service
public class MessageConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumerService.class);

    @Value("${kafka.topic.inmail-topic}")
    private String inmailTopic;
    @Value("${kafka.topic.event-topic}")
    private String eventTopic;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private TodoEventMapper todoEventMapper;
    @Resource
    private TodoEventService todoEventService;
    @Resource
    private UserApi userApi;

    private final InmailContentFormatter contentFormatter = new InmailContentFormatter();

    @KafkaListener(topics = {"${kafka.topic.inmail-topic}"}, groupId = "group1")
    public void consumeInmailMessage(ConsumerRecord<String, InmailMessage> record) {
        LOGGER.info("接收到来自topic[{}], partition[{}]的消息 --> {}", inmailTopic, record.partition(), record.value());

        //TODO：补充使用redis对消息进行幂等校验，防止重复消费
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
                message.setMsgType(PROCESS_CHANGE);
                message.setTo(reimburseSheet.getApplicantId());
                content = contentFormatter.processChangeContent(reimburseSheet, processNode);
                break;

            case NEW_EVENT:
                //DONE：待补充逻辑
                TodoEvent todoEvent = inmailMessage.getTodoEvent();
                message.setMsgType(NEW_EVENT);
                message.setTo(todoEvent.getTodoUser());
                content = contentFormatter.newEventContent(todoEvent);
                break;

            case PROCESS_SUPERVISE:
                //TODO：待补充逻辑
                break;

            case TIMEOUT_EVENT:
                //TODO：待补充定时查询超时事件的逻辑
                break;

            default:
                break;
        }
        message.setContent(content);
        messageMapper.insert(message);

        LOGGER.info("成功消费消息：topic[{}], id[{}]", inmailTopic, inmailMessage.getId());
    }

    @KafkaListener(topics = {"${kafka.topic.event-topic}"}, groupId = "group1")
    public void consumeEventMessage(ConsumerRecord<String, EventMessage> record) {
        LOGGER.info("接收到来自topic[{}], partition[{}]的消息 --> {}", eventTopic, record.partition(), record.value());
        EventMessage eventMessage = record.value();

        ProcessNode processNode = eventMessage.getProcessNode();
        ReimburseSheet reimburseSheet = eventMessage.getReimburseSheet();

        // 计算期望日期
        Date curTime = new Date(record.timestamp());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curTime);

        //TODO：待修改期望完成天数，暂给定默认值3天
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date expectTime = new Date(calendar.getTime().getTime());

        TodoEvent todoEvent = new TodoEvent();
        todoEvent.setId(IdGenerator.getUniqueId(TodoEvent.class));
        todoEvent.setType(processNode.getType());
        todoEvent.setSheetId(processNode.getSheetId());
        todoEvent.setTodoUser(processNode.getOprUser());
        todoEvent.setState(CommonState.CONTINUE.getVal());
        todoEvent.setCreateTime(curTime);
        todoEvent.setExpectTime(expectTime);

        StringBuilder descStr = new StringBuilder();
        descStr.append(NODE_TYPE_NAMES.get(processNode.getType()));
        descStr.append('-');
        descStr.append(reimburseSheet.getName());
        todoEvent.setDescription(descStr.toString());

        switch (processNode.getType()) {
            case ProcessNodeType.APPROVAL:
                todoEvent.setType(TodoEventType.APPROVAL);
                break;

            case ProcessNodeType.PAY:
                todoEvent.setType(TodoEventType.PAY);
                break;

            default:
                break;
        }
        // 待办事件入库
        todoEventMapper.insert(todoEvent);

        // 发起站内信通知-新的待办事件
        this.consumeInmailMessage(fakeInmailMessage(todoEvent));

        LOGGER.info("成功消费消息：topic[{}], id[{}]", eventTopic, eventMessage.getId());
    }

    /**
     * 为指定事件创建一个”虚拟“消息队列信息
     * @param todoEvent 指定事件
     * @return
     */
    private ConsumerRecord<String, InmailMessage> fakeInmailMessage(TodoEvent todoEvent) {

        InmailMessage inmailMessage = new InmailMessage();
        inmailMessage.setId(IdGenerator.getUniqueId(InmailMessage.class));
        inmailMessage.setInmailType(NEW_EVENT);
        inmailMessage.setTodoEvent(todoEvent);

        ConsumerRecord<String, InmailMessage> consumerRecord = new ConsumerRecord<String, InmailMessage>("fake", -1, -1,
                System.currentTimeMillis(), TimestampType.CREATE_TIME, -1, -1,
                null, inmailMessage, new RecordHeaders(), null);

        return consumerRecord;
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
        public String newEventContent(TodoEvent todoEvent) {
            //TODO：待补充额外逻辑
            Long userId = todoEvent.getTodoUser();

            int unhandledEventsNum = todoEventService.getUnhandledEventsNum(userId);
            int supervisedEventsNum = todoEventService.getSupervisedEventsNum(userId);
            int outOfTimeEventsNum = todoEventService.getOutOfTimeEventsNum(userId);

            return String.format(NEW_EVENT_FORMAT, unhandledEventsNum, supervisedEventsNum, outOfTimeEventsNum);
        }

    }
}
