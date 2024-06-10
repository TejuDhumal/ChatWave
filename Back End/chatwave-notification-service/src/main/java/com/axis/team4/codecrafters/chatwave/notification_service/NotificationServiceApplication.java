package com.axis.team4.codecrafters.chatwave.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.axis.team4.codecrafters.chatwave.notification_service")
@EnableJpaRepositories(basePackages = "com.axis.team4.codecrafters.chatwave.notification_service.repository")
@EnableDiscoveryClient
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
