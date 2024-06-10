package com.axis.ChatWave.controllers;

import com.axis.ChatWave.models.Chat;
import com.axis.ChatWave.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.saveChat(chat);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Chat>> getChatsByGroupId(@PathVariable Long groupId) {
        List<Chat> chats = chatService.getChatsByGroupId(groupId);
        return ResponseEntity.ok(chats);
    }
}

