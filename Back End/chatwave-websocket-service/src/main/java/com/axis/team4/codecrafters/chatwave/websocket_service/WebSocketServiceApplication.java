package com.axis.team4.codecrafters.chatwave.websocket_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WebSocketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketServiceApplication.class, args);
    }
}
