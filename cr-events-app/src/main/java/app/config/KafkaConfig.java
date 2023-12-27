package app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：用于kafka的配置类
 * <p>Date: 2023/12/27
 * <p>Time: 23:56
 *
 * @Author SillyBaka
 **/
@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.inmail-topic}")
    private String inmailTopic;
    @Value("${kafka.topic.event-topic}")
    private String eventTopic;

    /**
     * 交给spring创建topic
     */
    @Bean
    public NewTopic inmailTopic() {
        return new NewTopic(inmailTopic, 1, (short) 1);
    }
    @Bean
    public NewTopic eventTopic() {
        return new NewTopic(eventTopic, 1, (short) 1);
    }
}
