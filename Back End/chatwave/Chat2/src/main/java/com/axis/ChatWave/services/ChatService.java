package com.axis.ChatWave.services;

import com.axis.ChatWave.models.Chat;

import java.util.List;

public interface ChatService {
    Chat saveChat(Chat chat);
    List<Chat> getChatsByGroupId(Long groupId);
}

