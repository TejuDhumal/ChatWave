package com.axis.team4.codecrafters.messaging.repository.redis;
import org.springframework.data.repository.CrudRepository;
import com.axis.team4.codecrafters.messaging.model.Message;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {
	List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

	List<Message> findAll();
}
