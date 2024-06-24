// This class is responsible for handling HTTP requests to send and retrieve notifications.

package com.axis.team4.codecrafters.notification_service.controller;

import com.axis.team4.codecrafters.notification_service.model.NotificationRequest;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Queue notificationQueue;

 
    public NotificationController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, Queue notificationQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.notificationQueue = notificationQueue;
    }

// Send a notification to RabbitMQ  
    
    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            String notificationRequestJson = objectMapper.writeValueAsString(notificationRequest);
            rabbitTemplate.convertAndSend("notificationsExchange", "notificationKey", notificationRequestJson);
            logger.info("Notification sent to RabbitMQ: {}", notificationRequestJson);
        } catch (Exception e) {
            logger.error("Error sending notification to RabbitMQ", e);
            // Handle exception
        }
    }

// Retrieve notifications from RabbitMQ    
    
    @GetMapping("/retrieve")
    public List<NotificationRequest> retrieveNotifications() {
        List<NotificationRequest> notifications = new ArrayList<>();
        boolean running = true;

        while (running) {
            Message message = rabbitTemplate.receive(notificationQueue.getName());
            if (message != null) {
                try {
                    NotificationRequest notificationRequest = objectMapper.readValue(message.getBody(), NotificationRequest.class);
                    notifications.add(notificationRequest);
                } catch (Exception e) {
                    logger.error("Error processing message from RabbitMQ", e);
                }
            } else {
                running = false; // Exit the loop if there are no more messages
            }
        }

        return notifications;
    }
}
