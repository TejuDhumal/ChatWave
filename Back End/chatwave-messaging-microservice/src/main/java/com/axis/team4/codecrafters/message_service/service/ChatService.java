//Purpose: Interface for ChatService.

package com.axis.team4.codecrafters.message_service.service;

import java.util.List;

import com.axis.team4.codecrafters.message_service.exception.ChatException;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.Chat;
import com.axis.team4.codecrafters.message_service.request.GroupChatRequest;

public interface ChatService {

	Chat createChat(Integer reqUserId, Integer userId2, boolean isGroup) throws UserException;

	Chat findChatById(Integer chatId) throws ChatException;

	List<Chat> findAllChatByUserId(Integer userId) throws UserException;

	Chat createGroup(GroupChatRequest req, Integer reqUserId) throws UserException;

	Chat addUsersToGroup(List<Integer> userIds, Integer chatId) throws UserException, ChatException;

	Chat renameGroup(Integer chatId, String groupName, String groupImage, Integer reqUserId)
			throws ChatException, UserException;

	Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException;

	Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

	Chat exitFromGroup(Integer chatId, Integer userId) throws UserException, ChatException;

	Chat blockChat(Integer chatId, Integer userId) throws UserException, ChatException;

	Chat unblockChat(Integer chatId, Integer userId) throws UserException, ChatException;

}
