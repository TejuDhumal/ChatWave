package com.axis.team4.codecrafters.chatwave.chathistory.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.axis.team4.codecrafters.chatwave.chathistory.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
