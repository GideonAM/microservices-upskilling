package com.redeemerlives.order_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BeanConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("order-topic")
                .build();
    }
}
