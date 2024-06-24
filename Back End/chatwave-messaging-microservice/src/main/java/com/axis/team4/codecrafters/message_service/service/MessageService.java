// Purpose: Service interface for message service.

package com.axis.team4.codecrafters.message_service.service;

import java.util.List;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.MessageException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.Message;
import com.axis.team4.codecrafters.message_service.request.SendMessageRequest;


public interface MessageService  {
	
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;

	public Message editMessage(Integer messageId, String newContent) throws MessageException;

	
	public String deleteMessage(Integer messageId) throws MessageException;

}
