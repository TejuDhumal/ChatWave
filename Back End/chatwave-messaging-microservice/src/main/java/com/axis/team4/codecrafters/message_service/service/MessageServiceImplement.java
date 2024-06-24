// Purpose: Implementation of the MessageService interface. It provides the implementation of the methods declared in the MessageService interface. It provides the implementation of the sendMessage, editMessage, deleteMessage, getChatsMessages, and findMessageById methods. It also sends a notification to the notification microservice when a new message is sent.

package com.axis.team4.codecrafters.message_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.MessageException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.Chat;
import com.axis.team4.codecrafters.message_service.modal.Message;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.repository.jpa.MessageRepository;
import com.axis.team4.codecrafters.message_service.repository.redis.MessageRepositoryRedis;
import com.axis.team4.codecrafters.message_service.request.SendMessageRequest;

@Service
public class MessageServiceImplement implements MessageService {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private MessageRepositoryRedis messageRepositoryRedis;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    // This method is used to send a message to a chat.

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimeStamp(LocalDateTime.now());
        message.setIs_read(false);

        message = messageRepo.save(message);
        messageRepositoryRedis.save(message);
        
        return message;
    }
    
    //	This method is used to edit a message. 

    @Override
    public Message editMessage(Integer messageId, String newContent) throws MessageException {
        Message message = findMessageById(messageId);
        message.setContent(newContent);

        message = messageRepo.save(message);
        messageRepositoryRedis.save(message);

        return message;
    }
    
    //    This method is used to delete a message.

    @Override
    public String deleteMessage(Integer messageId) throws MessageException {
        Message message = findMessageById(messageId);
        message.setContent("This message was deleted");

        messageRepo.save(message);
        messageRepositoryRedis.save(message);

        return "message content updated to 'message deleted'";
    }
    
    //    This method is used to get all messages from a chat.

    @Override
    public List<Message> getChatsMessages(Integer chatId) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        List<Message> messages = messageRepo.findMessageByChatId(chatId);
        return messages;
    }
    
    //    This method is used to find a message by its id.

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> message = messageRepo.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        }
        throw new MessageException("message not exist with id " + messageId);
    }
}
