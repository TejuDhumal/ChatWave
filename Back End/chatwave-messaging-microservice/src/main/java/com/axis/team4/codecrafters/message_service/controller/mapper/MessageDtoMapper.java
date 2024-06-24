// Purpose: Contains the MessageDtoMapper class which is used to map the Message object to MessageDto object.

package com.axis.team4.codecrafters.message_service.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.axis.team4.codecrafters.message_service.dto.ChatDto;
import com.axis.team4.codecrafters.message_service.dto.MessageDto;
import com.axis.team4.codecrafters.message_service.dto.UserDto;
import com.axis.team4.codecrafters.message_service.modal.Message;

public class MessageDtoMapper {
	
	
	public static MessageDto toMessageDto(Message message) {
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(message.getChat());
		UserDto userDto=UserDtoMapper.toUserDTO(message.getUser());
		
		MessageDto messageDto=new MessageDto();
		messageDto.setId(message.getId());
		messageDto.setChat(chatDto);
		messageDto.setContent(message.getContent());
		messageDto.setIs_read(message.getIs_read());
		messageDto.setTimeStamp(message.getTimeStamp());
		messageDto.setIsAttachment(message.getIsAttachment());
		messageDto.setUser(userDto);
//		messageDto.set
		
		return messageDto;
	}
	
	public static List<MessageDto> toMessageDtos(List<Message> messages){
		
		List<MessageDto> messageDtos=new ArrayList<>();
		
		for(Message message : messages) {
			MessageDto messageDto=toMessageDto(message);
			messageDtos.add(messageDto);
		}
		
		return messageDtos;
	}

}