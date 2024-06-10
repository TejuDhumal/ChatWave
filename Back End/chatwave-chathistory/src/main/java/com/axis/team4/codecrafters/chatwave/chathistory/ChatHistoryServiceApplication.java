package com.axis.team4.codecrafters.chatwave.chathistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ChatHistoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatHistoryServiceApplication.class, args);
    }
}
