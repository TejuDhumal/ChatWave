
package com.axis.team4.codecrafters.messaging.config;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@EnableMongoRepositories(basePackages = "com.axis.team4.codecrafters.messaging.repository.mongo")
@EnableRedisRepositories(basePackages = "com.axis.team4.codecrafters.messaging.repository.redis")
public class RedisConfig {

    @Bean
    public RedisTemplate<String, LocalDateTime> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, LocalDateTime> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(String.class));
        template.setValueSerializer(new LocalDateTimeRedisSerializer());
        return template;
    }
}
