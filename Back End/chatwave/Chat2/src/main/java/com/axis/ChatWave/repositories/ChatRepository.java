package com.axis.ChatWave.repositories;

import com.axis.ChatWave.models.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByGroupId(Long groupId);
}
