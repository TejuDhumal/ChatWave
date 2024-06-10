package com.axis.team4.codecrafters.chatwave.chathistory.service;

import com.axis.team4.codecrafters.chatwave.chathistory.model.ChatMessage;

import java.util.List;

public interface ChatHistoryService {
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getChatHistory(String senderId, String receiverId);
}
