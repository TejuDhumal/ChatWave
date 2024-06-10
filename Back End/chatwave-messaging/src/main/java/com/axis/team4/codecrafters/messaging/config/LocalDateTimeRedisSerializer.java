package com.axis.team4.codecrafters.messaging.config;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeRedisSerializer implements RedisSerializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public byte[] serialize(LocalDateTime localDateTime) throws SerializationException {
        return localDateTime.format(formatter).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public LocalDateTime deserialize(byte[] bytes) throws SerializationException {
        return LocalDateTime.parse(new String(bytes, StandardCharsets.UTF_8), formatter);
    }
}
