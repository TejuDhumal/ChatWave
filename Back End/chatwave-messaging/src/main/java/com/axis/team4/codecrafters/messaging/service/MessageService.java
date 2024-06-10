
package com.axis.team4.codecrafters.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.messaging.model.Message;
import com.axis.team4.codecrafters.messaging.repository.mongo.MessageMongoRepository;
import com.axis.team4.codecrafters.messaging.repository.redis.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageMongoRepository messageMongoRepository;

	public Message sendMessage(Message message) {
		message.setTimestamp(LocalDateTime.now());
		messageRepository.save(message);
		messageMongoRepository.save(message);
		return message;
	}

	public List<Message> getMessages(String senderId, String receiverId) {
		// Retrieve all messages from Redis
		List<Message> allMessages = messageRepository.findAll();

		// Filter the messages to only include the ones between the specific sender and
		// receiver
		List<Message> filteredMessages = allMessages.stream().filter(
				message -> (message.getSenderId().equals(senderId) && message.getReceiverId().equals(receiverId))
						|| (message.getSenderId().equals(receiverId) && message.getReceiverId().equals(senderId)))
				.collect(Collectors.toList());

		return filteredMessages;
	}

	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	public void deleteMessage(String id) {
		messageRepository.deleteById(id);
		messageMongoRepository.deleteById(id);
	}
}
