// Purpose: Contains the repository interface for the Message entity. It extends JpaRepository interface. It contains a method to find messages by chat id.

package com.axis.team4.codecrafters.message_service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.axis.team4.codecrafters.message_service.modal.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m join m.chat c where c.id=:chatId")
	public List<Message> findMessageByChatId(@Param("chatId") Integer chatId);

}
