// This file contains the configuration for the application. It sets up the security configuration for the application and also provides a password encoder bean.

package com.axis.team4.codecrafters.message_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityAppConfig(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**,/ws/**,/socket.io/**,/wss/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
