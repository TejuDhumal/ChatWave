package com.axis.team4.codecrafters.messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axis.team4.codecrafters.messaging.model.Message;
import com.axis.team4.codecrafters.messaging.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String senderId, @PathVariable String receiverId) {
        List<Message> messages = messageService.getMessages(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
        
    }
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}
