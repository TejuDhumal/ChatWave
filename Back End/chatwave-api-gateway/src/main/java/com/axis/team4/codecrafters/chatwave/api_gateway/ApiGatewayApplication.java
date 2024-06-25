package com.axis.team4.codecrafters.chatwave.api_gateway;

import com.axis.team4.codecrafters.chatwave.api_gateway.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("cw-user-service", r -> r.path("/auth/**", "/api/users/**").uri("lb://CW-USER-SERVICE"))
				.route("cw-message-service",
						r -> r.path("/api/messages/**", "/api/chats/**", "/message/**", "/ws/**,/socket.io/**,/wss/**").uri("lb://CW-MESSAGE-SERVICE"))
				.route("cw-notification-service", r -> r.path("/notifications/**").uri("lb://CW-NOTIFICATION-SERVICE"))
				.route("cw-chat-history-service", r -> r.path("/api/chat-history/**").uri("lb://CW-CHAT-HISTORY-SERVICE"))
				.build();
	}
}
