// Purpose: Implementation of the ChatService interface.

package com.axis.team4.codecrafters.message_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.Chat;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.repository.jpa.ChatRepository;
import com.axis.team4.codecrafters.message_service.repository.jpa.UserRepository;
import com.axis.team4.codecrafters.message_service.repository.mongodb.ChatRepositoryMongo;
import com.axis.team4.codecrafters.message_service.request.GroupChatRequest;

@Service
public class ChatServiceImplementation implements ChatService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private ChatRepositoryMongo chatRepositoryMongo;

    @Autowired
    private UserRepository userRepo;
    
    // This method is used to create a chat between two users.

    @Override
    public Chat createChat(Integer reqUserId, Integer userId2, boolean isGroup) throws UserException {
        User reqUser = userService.findUserById(reqUserId);
        User user2 = userService.findUserById(userId2);

        Chat existingChat = chatRepo.findSingleChatByUsersId(user2, reqUser);
        if (existingChat != null) {
            return existingChat;
        }

        Chat chat = new Chat();
        chat.setCreated_by(reqUser);
        chat.getUsers().add(reqUser);
        chat.getUsers().add(user2);
        chat.setIs_group(false);

        chat = chatRepo.save(chat);
        chatRepositoryMongo.save(chat);

        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepo.findById(chatId);
        if (chat.isPresent()) {
            return chat.get();
        }
        throw new ChatException("Chat does not exist with id " + chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return chatRepo.findChatByUserId(user.getId());
    }

    @Override
    public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        User user = userService.findUserById(userId);
        Chat chat = findChatById(chatId);

        if (chat.getCreated_by().getId().equals(user.getId()) && !chat.getIs_group()) {
            chatRepo.deleteById(chat.getId());
            chatRepositoryMongo.deleteById(chat.getId());
            return chat;
        }

        throw new ChatException("You do not have access to delete this chat");
    }

    @Override
    public Chat createGroup(GroupChatRequest req, Integer reqUserId) throws UserException {
        User reqUser = userService.findUserById(reqUserId);

        Chat chat = new Chat();
        chat.setCreated_by(reqUser);
        chat.getUsers().add(reqUser);

        for (Integer userId : req.getUserIds()) {
            User user = userService.findUserById(userId);
            if (user != null) {
                chat.getUsers().add(user);
            } else {
                throw new UserException("User not found with id " + userId);
            }
        }

        chat.setChat_name(req.getChat_name());
        chat.setChat_image(req.getChat_image());
        chat.setIs_group(true);
        chat.getAdmins().add(reqUser);

        chat = chatRepo.save(chat);
        chatRepositoryMongo.save(chat);

        return chat;
    }

    @Override
    public Chat addUsersToGroup(List<Integer> userIds, Integer chatId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        List<User> usersToAdd = new ArrayList<>();

        for (Integer userId : userIds) {
            User user = userService.findUserById(userId);
            if (!chat.getUsers().contains(user)) {
                usersToAdd.add(user);
            } else {
                throw new ChatException("User with id " + userId + " is already in the group");
            }
        }

        chat.getUsers().addAll(usersToAdd);
        chat = chatRepo.save(chat);
        chatRepositoryMongo.save(chat);

        return chat;
    }
    
    // This method is used to remove a user from a group chat.

    @Override
    public Chat renameGroup(Integer chatId, String groupName, String groupImage, Integer reqUserId) throws ChatException, UserException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(reqUserId);

        if (chat.getAdmins().contains(user)) {
            chat.setChat_name(groupName);
            chat.setChat_image(groupImage);
            chat = chatRepo.save(chat);
            
            chatRepositoryMongo.save(chat);
        } else {
            throw new ChatException("You do not have permission to rename this group");
        }

        return chat;
    }
    
    // This method is used to remove a user from a group chat.

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(userId);
        User reqUser = userService.findUserById(reqUserId);

        if (chat.getAdmins().contains(reqUser)) {
            if (chat.getUsers().remove(user)) {
                chat = chatRepo.save(chat);
                chatRepositoryMongo.save(chat);
            } else {
                throw new ChatException("User not found in the group");
            }
        } else {
            throw new ChatException("You do not have access to remove this user from the group");
        }

        return chat;
    }
    
    // This method is used to exit a user from a group chat.
    
    @Override
    public Chat exitFromGroup(Integer chatId, Integer userId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(userId);

        if (chat.getUsers().contains(user)) {
            chat.getUsers().remove(user);
            chat = chatRepo.save(chat);
            chatRepositoryMongo.save(chat);
        } else {
            throw new ChatException("User not found in the group");
        }

        return chat;
    }
    
    	
    
    @Override
    public Chat blockChat(Integer chatId, Integer userId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(userId);

        if (!chat.getUsers().contains(user)) {
            throw new ChatException("User not found in the chat");
        }

        chat.setBlocked(true);
        chat.setBlocked_by(user.getId()); // Set the user who blocked the chat
        chat = chatRepo.save(chat);
        chatRepositoryMongo.save(chat);

        return chat;
    }
    
    // This method is used to unblock a chat.
    
    @Override
    public Chat unblockChat(Integer chatId, Integer userId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(userId);

        if (!chat.getUsers().contains(user)) {
            throw new ChatException("User not found in the chat");
        }

        chat.setBlocked(false);
        chat.setBlocked_by(null); // Reset the blocked_by field
        chat = chatRepo.save(chat);
        chatRepositoryMongo.save(chat);

        return chat;
    }
    
}
