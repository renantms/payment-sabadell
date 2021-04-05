package br.com.invillia.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopics {

    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("payment")
                            .build();
    }

}
