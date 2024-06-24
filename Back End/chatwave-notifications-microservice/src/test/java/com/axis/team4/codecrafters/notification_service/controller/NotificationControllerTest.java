package com.axis.team4.codecrafters.notification_service.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.axis.team4.codecrafters.notification_service.model.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;

//@Disabled
class NotificationControllerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Queue notificationQueue;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() throws Exception {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        String notificationRequestJson = "{\"message\":\"Test message\"}";

        when(objectMapper.writeValueAsString(notificationRequest)).thenReturn(notificationRequestJson);

        // When
        notificationController.sendNotification(notificationRequest);

        // Then
        verify(rabbitTemplate, times(1)).convertAndSend("notificationsExchange", "notificationKey", notificationRequestJson);
    }

    @Test
    void testRetrieveNotifications() throws Exception {
        // Given
        NotificationRequest notificationRequest = new NotificationRequest();
        String notificationRequestJson = "{\"message\":\"Test message\"}";
        Message message = mock(Message.class);
        List<NotificationRequest> expectedNotifications = new ArrayList<>();
        expectedNotifications.add(notificationRequest);

        when(notificationQueue.getName()).thenReturn("notificationQueue");
        when(rabbitTemplate.receive("notificationQueue")).thenReturn(message).thenReturn(null);
        when(message.getBody()).thenReturn(notificationRequestJson.getBytes());
        when(objectMapper.readValue(notificationRequestJson.getBytes(), NotificationRequest.class)).thenReturn(notificationRequest);

        // When
        List<NotificationRequest> notifications = notificationController.retrieveNotifications();

        // Then
        assertNotNull(notifications);
        assertEquals(expectedNotifications, notifications);
    }
}
