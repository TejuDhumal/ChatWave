// This is the ChatController class which is used to handle the requests for the chat service.

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

import com.axis.team4.codecrafters.message_service.controller.mapper.ChatDtoMapper;
import com.axis.team4.codecrafters.message_service.dto.ChatDto;
import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.modal.Chat;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.request.GroupChatRequest;
import com.axis.team4.codecrafters.message_service.request.RenameGroupRequest;
import com.axis.team4.codecrafters.message_service.request.SingleChatRequest;
import com.axis.team4.codecrafters.message_service.request.UserIdsRequest;
import com.axis.team4.codecrafters.message_service.service.ChatService;
import com.axis.team4.codecrafters.message_service.service.UserService;


@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    // Create a single chat
    
    @PostMapping("/single")
    public ResponseEntity<?> creatChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws ChatException {
        try {
            System.out.println("single chat --------");
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.createChat(reqUser.getId(), singleChatRequest.getUserId(), false);
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Create a group chat

    @PostMapping("/group")
    public ResponseEntity<?> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws ChatException {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.createGroup(groupChatRequest, reqUser.getId());
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Retrieve a chat by id

    @GetMapping("/{chatId}")
    public ResponseEntity<?> findChatByIdHandler(@PathVariable Integer chatId) {
        try {
            Chat chat = chatService.findChatById(chatId);
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Retrieve all chats by user id

    @GetMapping("/user")
    public ResponseEntity<?> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfile(jwt);
            List<Chat> chats = chatService.findAllChatByUserId(user.getId());
            List<ChatDto> chatDtos = ChatDtoMapper.toChatDtos(chats);
            return new ResponseEntity<>(chatDtos, HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Add users to a group chat

    @PutMapping("/{chatId}/add-users")
    public ResponseEntity<?> addUsersToGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId, @RequestBody UserIdsRequest userIdsRequest) {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.addUsersToGroup(userIdsRequest.getUserIds(), chatId);
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Rename a group chat

    @PutMapping("/{chatId}/rename")
    public ResponseEntity<?> renameGroupHandler(@PathVariable Integer chatId, @RequestBody RenameGroupRequest renameGoupRequest, @RequestHeader("Authorization") String jwt) {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.renameGroup(chatId, renameGoupRequest.getGroupName(), renameGoupRequest.getGroupImage(), reqUser.getId());
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Remove a user from a group chat

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<?> removeFromGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId, @PathVariable Integer userId) {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.removeFromGroup(chatId, userId, reqUser.getId());
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Delete a chat

    @DeleteMapping("/delete/{chatId}/{userId}")
    public ResponseEntity<?> deleteChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId) {
        try {
            Chat chat = chatService.deleteChat(chatId, userId);
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Exit from a group chat
    
    @PutMapping("/{chatId}/exit")
    public ResponseEntity<?> exitFromGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.exitFromGroup(chatId, reqUser.getId());
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Block a chat
    
    @PutMapping("/{chatId}/block")
    public ResponseEntity<?> blockChatHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) {
        try {
            User reqUser = userService.findUserProfile(jwt);
            Chat chat = chatService.blockChat(chatId, reqUser.getId());
            ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
        } catch (UserException | ChatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Unblock a chat

	@PutMapping("/{chatId}/unblock")
	public ResponseEntity<?> unblockChatHandler(@RequestHeader("Authorization") String jwt,
			@PathVariable Integer chatId) {
		try {
			User reqUser = userService.findUserProfile(jwt);
			Chat chat = chatService.unblockChat(chatId, reqUser.getId());
			ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
			return new ResponseEntity<>(chatDto, HttpStatus.OK);
		} catch (UserException | ChatException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
