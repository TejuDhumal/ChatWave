package com.axis.team4.codecrafters.chatwave.chathistory.service;

import com.axis.team4.codecrafters.chatwave.chathistory.model.ChatMessage;
import com.axis.team4.codecrafters.chatwave.chathistory.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getChatHistory(String senderId, String receiverId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }
}
