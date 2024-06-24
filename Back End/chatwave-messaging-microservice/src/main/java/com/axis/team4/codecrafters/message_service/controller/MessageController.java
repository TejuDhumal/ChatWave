// This file contains the controller for messages, which includes the following functionalities:

package com.axis.team4.codecrafters.message_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.MessageException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.request.EditMessageRequest;
import com.axis.team4.codecrafters.message_service.request.SendMessageRequest;
import com.axis.team4.codecrafters.message_service.response.ApiResponse;
import com.axis.team4.codecrafters.message_service.controller.mapper.MessageDtoMapper;
import com.axis.team4.codecrafters.message_service.dto.MessageDto;
import com.axis.team4.codecrafters.message_service.modal.Message;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.service.MessageService;
import com.axis.team4.codecrafters.message_service.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    // This method is used to handle the request for sending a message to a chat.
    
    @PostMapping("/create")
    public ResponseEntity<MessageDto> sendMessageHandler(@RequestHeader("Authorization") String jwt, @RequestBody SendMessageRequest req) throws UserException, ChatException {

        User reqUser = userService.findUserProfile(jwt);

        req.setUserId(reqUser.getId());

        Message message = messageService.sendMessage(req);

        MessageDto messageDto = MessageDtoMapper.toMessageDto(message);

        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.OK);
    }
    
    // This method is used to handle the request for retrieving messages from a chat.

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDto>> getChatsMessageHandler(@PathVariable Integer chatId) throws ChatException {

        List<Message> messages = messageService.getChatsMessages(chatId);

        List<MessageDto> messageDtos = MessageDtoMapper.toMessageDtos(messages);

        return new ResponseEntity<List<MessageDto>>(messageDtos, HttpStatus.ACCEPTED);

    }
    
    // This method is used to handle the request for deleting a message.

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId) throws MessageException {

        messageService.deleteMessage(messageId);

        ApiResponse res = new ApiResponse("message deleted successfully", true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }
    
    // This method is used to handle the request for editing a message.

    @PutMapping("/{messageId}")
    public ResponseEntity<MessageDto> editMessageHandler(@PathVariable Integer messageId, @RequestBody EditMessageRequest editMessageRequest) throws MessageException {
        Message updatedMessage = messageService.editMessage(messageId, editMessageRequest.getNewContent());

        MessageDto messageDto = MessageDtoMapper.toMessageDto(updatedMessage);

        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.OK);
    }

}
