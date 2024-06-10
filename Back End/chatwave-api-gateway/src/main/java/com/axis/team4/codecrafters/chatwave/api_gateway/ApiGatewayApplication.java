package com.axis.team4.codecrafters.chatwave.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("chatwave-usermanagement", r -> r.path("/users/**")
                        .uri("lb://CHATWAVE-USERMANAGEMENT"))
                .route("chatwave-messaging", r -> r.path("/messages/**")
                        .uri("lb://CHATWAVE-MESSAGING"))
                .route("chatwave-notification", r -> r.path("/notifications/**")
                        .uri("lb://CHATWAVE-NOTIFICATION-SERVICE"))
                .route("chatwave-chathistory", r -> r.path("/chat-history/**")
                        .uri("lb://CHATWAVE-CHATHISTORY"))
                .route("chatwave-websocket", r -> r.path("/websocket/**")
                        .uri("lb://CHATWAVE-WEBSOCKET-SERVICE"))
                .build();
    }
}
