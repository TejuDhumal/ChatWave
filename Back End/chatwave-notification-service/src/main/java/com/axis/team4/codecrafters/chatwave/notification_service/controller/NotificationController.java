package com.axis.team4.codecrafters.chatwave.notification_service.controller;

import com.axis.team4.codecrafters.chatwave.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/sendNotification")
    public String sendNotification(@RequestParam String message) {
        notificationService.sendNotification(message);
        return "Notification sent!";
    }
}
