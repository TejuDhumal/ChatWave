package com.axis.team4.codecrafters.messaging.service;

import com.axis.team4.codecrafters.messaging.model.Message;
import com.axis.team4.codecrafters.messaging.repository.redis.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessageServiceImpl extends MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Message sendMessage(Message message) {
		message.setTimestamp(LocalDateTime.now());
		logger.debug("Saving message: {}", message);
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getMessages(String senderId, String receiverId) {
		logger.debug("Getting messages for senderId: {} and receiverId: {}", senderId, receiverId);
		List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
		if (messages.isEmpty()) {
			logger.debug("No messages found for senderId: {} and receiverId: {}", senderId, receiverId);
		} else {
			logger.debug("Messages retrieved: {}", messages);
		}
		return messages;
	}

	@Override
	public List<Message> getAllMessages() {
		logger.debug("Getting all messages");
		List<Message> messages = messageRepository.findAll();
		logger.debug("Messages retrieved: {}", messages);
		return messages;
	}

	@Override
	public void deleteMessage(String id) {
		logger.debug("Deleting message with id: {}", id);
		messageRepository.deleteById(id);
	}
}
