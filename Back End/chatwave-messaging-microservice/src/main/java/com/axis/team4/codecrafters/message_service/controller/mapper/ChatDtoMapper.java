// Purpose: Contains the ChatDtoMapper class which is responsible for mapping Chat objects to ChatDto objects.

package com.axis.team4.codecrafters.message_service.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.axis.team4.codecrafters.message_service.dto.ChatDto;
import com.axis.team4.codecrafters.message_service.dto.MessageDto;
import com.axis.team4.codecrafters.message_service.dto.UserDto;
import com.axis.team4.codecrafters.message_service.modal.Chat;

public class ChatDtoMapper {

    public static ChatDto toChatDto(Chat chat) {
        UserDto userDto = UserDtoMapper.toUserDTO(chat.getCreated_by());
        List<MessageDto> messageDtos = MessageDtoMapper.toMessageDtos(chat.getMessages());
        Set<UserDto> userDtos = UserDtoMapper.toUserDtos(chat.getUsers());
        Set<UserDto> admins = UserDtoMapper.toUserDtos(chat.getAdmins());

        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setChat_image(chat.getChat_image());
        chatDto.setChat_name(chat.getChat_name());
        chatDto.setCreated_by(userDto);
        chatDto.setIs_group(chat.getIs_group());
        chatDto.setBlocked(chat.getBlocked());

        // Set the blocked_by field by extracting the ID if the user exists
        if (chat.getBlocked_by() != null) {
            chatDto.setBlocked_by(chat.getBlocked_by());
        } else {
            chatDto.setBlocked_by(null); // Explicitly set to null if no user
        }

        chatDto.setMessages(messageDtos);
        chatDto.setUsers(userDtos);
        chatDto.setAdmins(admins);

        return chatDto;
    }

    public static List<ChatDto> toChatDtos(List<Chat> chats) {
        List<ChatDto> chatDtos = new ArrayList<>();

        for (Chat chat : chats) {
            ChatDto chatDto = toChatDto(chat);
            chatDtos.add(chatDto);
        }

        return chatDtos;
    }
}