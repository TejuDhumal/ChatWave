package com.axis.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;


@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests(authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/forgot/forgot-password", "/forgot/reset-password").permitAll()
//                    .anyRequest().authenticated()
//            );
//
//        return http.build();
//    }

//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder() throws Exception {
//        AuthenticationManagerBuilder auth = new AuthenticationManagerBuilder(null);
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        return auth;
//    }
}
