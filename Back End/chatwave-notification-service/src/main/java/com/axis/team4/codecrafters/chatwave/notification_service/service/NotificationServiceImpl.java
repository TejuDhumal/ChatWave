package com.axis.team4.codecrafters.chatwave.notification_service.service;

import com.axis.team4.codecrafters.chatwave.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend("notificationQueue", message);
    }
}
