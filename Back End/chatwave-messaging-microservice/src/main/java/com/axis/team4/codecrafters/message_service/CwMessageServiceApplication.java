// This is the main class for the message service module. This class is responsible for starting the Spring Boot application and starting the SocketIO server.

package com.axis.team4.codecrafters.message_service;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.axis.team4.codecrafters.message_service")
@EnableJpaRepositories(basePackages = "com.axis.team4.codecrafters.message_service.repository.jpa")
@EnableMongoRepositories(basePackages = "com.axis.team4.codecrafters.message_service.repository.mongodb")
@EnableRedisRepositories(basePackages = "com.axis.team4.codecrafters.message_service.repository.redis")
public class CwMessageServiceApplication implements CommandLineRunner {

    @Autowired
    private SocketIOServer socketIOServer;

    public static void main(String[] args) {
        SpringApplication.run(CwMessageServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> socketIOServer.stop()));
    }
}
