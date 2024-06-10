package com.axis.ChatWave.services;

import com.axis.ChatWave.models.Chat;
import com.axis.ChatWave.repositories.ChatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat saveChat(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getChatsByGroupId(Long groupId) {
        return chatRepository.findByGroupId(groupId);
    }
}

