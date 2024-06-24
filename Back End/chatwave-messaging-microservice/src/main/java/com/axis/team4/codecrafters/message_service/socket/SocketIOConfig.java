// Purpose: Configuration class for the SocketIO server. It sets the hostname, port and origin for the server.

package com.axis.team4.codecrafters.message_service.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9092);
        config.setOrigin("*"); // Allow all origins

        return new SocketIOServer(config);
    }
}
