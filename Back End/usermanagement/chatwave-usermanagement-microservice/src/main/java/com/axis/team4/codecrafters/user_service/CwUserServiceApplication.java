//This is the main class of the user service.

package com.axis.team4.codecrafters.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CwUserServiceApplication {
  public static void main(String[] args)
  {
    SpringApplication.run(CwUserServiceApplication.class, args);
  }
}
