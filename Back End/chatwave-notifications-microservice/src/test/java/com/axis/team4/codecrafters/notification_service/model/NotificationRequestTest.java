package com.axis.team4.codecrafters.notification_service.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

//@Disabled
class NotificationRequestTest {

    @Test
    void testNotificationRequestDefaultConstructor() {
        NotificationRequest notificationRequest = new NotificationRequest();
        assertNull(notificationRequest.getChatId());
        assertNull(notificationRequest.getUserId());
        assertNull(notificationRequest.getMessage());
    }

    @Test
    void testNotificationRequestParameterizedConstructor() {
        NotificationRequest notificationRequest = new NotificationRequest(1, 2, "Test message");
        assertEquals(1, notificationRequest.getChatId());
        assertEquals(2, notificationRequest.getUserId());
        assertEquals("Test message", notificationRequest.getMessage());
    }

    @Test
    void testGetChatId() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setChatId(1);
        assertEquals(1, notificationRequest.getChatId());
    }

    @Test
    void testSetChatId() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setChatId(1);
        assertEquals(1, notificationRequest.getChatId());
    }

    @Test
    void testGetUserId() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(2);
        assertEquals(2, notificationRequest.getUserId());
    }

    @Test
    void testSetUserId() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(2);
        assertEquals(2, notificationRequest.getUserId());
    }

    @Test
    void testGetMessage() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setMessage("Test message");
        assertEquals("Test message", notificationRequest.getMessage());
    }

    @Test
    void testSetMessage() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setMessage("Test message");
        assertEquals("Test message", notificationRequest.getMessage());
    }
}
