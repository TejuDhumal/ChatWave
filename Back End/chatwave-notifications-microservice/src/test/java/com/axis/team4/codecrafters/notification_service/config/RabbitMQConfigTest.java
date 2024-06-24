package com.axis.team4.codecrafters.notification_service.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

//@Disabled
class RabbitMQConfigTest {

    private AutoCloseable closeable;

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testNotificationQueue() {
        // When
        Queue queue = rabbitMQConfig.notificationQueue();

        // Then
        assertNotNull(queue);
        assertEquals("notificationQueue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void testNotificationsExchange() {
        // When
        TopicExchange exchange = rabbitMQConfig.notificationsExchange();

        // Then
        assertNotNull(exchange);
        assertEquals("notificationsExchange", exchange.getName());
    }

    @Test
    void testBinding() {
        // Given
        Queue queue = rabbitMQConfig.notificationQueue();
        TopicExchange exchange = rabbitMQConfig.notificationsExchange();

        // When
        Binding binding = rabbitMQConfig.binding(queue, exchange);

        // Then
        assertNotNull(binding);
        assertEquals("notificationQueue", binding.getDestination());
        assertEquals("notificationsExchange", binding.getExchange());
        assertEquals("notificationKey", binding.getRoutingKey());
    }
}
