// This file is used to configure the JPA repository for the message service.

package com.axis.team4.codecrafters.message_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.axis.team4.codecrafters.message_service.repository.jpa")
public class JpaConfig {
}
