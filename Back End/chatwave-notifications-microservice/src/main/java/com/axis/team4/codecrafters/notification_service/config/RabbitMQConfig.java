// This class is used to configure RabbitMQ for the notification service.

package com.axis.team4.codecrafters.notification_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue", true);
    }

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange("notificationsExchange");
    }

    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange notificationsExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationsExchange).with("notificationKey");
    }
}
