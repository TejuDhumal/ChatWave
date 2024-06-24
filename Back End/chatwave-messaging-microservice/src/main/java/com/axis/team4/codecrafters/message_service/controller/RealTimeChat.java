// This class is used to handle the real-time chat functionality of the application. It contains methods to receive and send messages from/to users. The messages are broadcasted to the public topic or sent to a specific user.

package com.axis.team4.codecrafters.message_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.Chat;
import com.axis.team4.codecrafters.message_service.modal.Message;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.request.SendMessageRequest;
import com.axis.team4.codecrafters.message_service.service.ChatService;
import com.axis.team4.codecrafters.message_service.service.MessageService;
import com.axis.team4.codecrafters.message_service.service.UserService;

@RestController
public class RealTimeChat {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;
    
    // This method is used to receive a message from a user and broadcast it to the public topic.

    @MessageMapping("/message")
    @SendTo("/topic/public")
    public Message receiveMessage(@Payload Message message) {
        return message; // Broadcast to public topic
    }
    
    // This method is used to receive a message from a user and send it to a specific user.

    @MessageMapping("/chat/{groupId}")
    public void sendToUser(@Payload SendMessageRequest req, @Header("Authorization") String jwt, @DestinationVariable String groupId) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Chat chat = chatService.findChatById(req.getChatId());
        Message createdMessage = messageService.sendMessage(req);
        simpMessagingTemplate.convertAndSend("/queue/" + groupId, createdMessage);
    }
}
