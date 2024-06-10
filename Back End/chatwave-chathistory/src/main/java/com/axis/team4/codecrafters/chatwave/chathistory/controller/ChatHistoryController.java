package com.axis.team4.codecrafters.chatwave.chathistory.controller;

import com.axis.team4.codecrafters.chatwave.chathistory.model.ChatMessage;
import com.axis.team4.codecrafters.chatwave.chathistory.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-history")
public class ChatHistoryController {

    @Autowired
    private ChatHistoryService chatHistoryService;

    @PostMapping("/save")
    public ChatMessage saveMessage(@RequestBody ChatMessage message) {
        return chatHistoryService.saveMessage(message);
    }

    @GetMapping("/get")
    public List<ChatMessage> getChatHistory(@RequestParam String senderId, @RequestParam String receiverId) {
        return chatHistoryService.getChatHistory(senderId, receiverId);
    }
}
