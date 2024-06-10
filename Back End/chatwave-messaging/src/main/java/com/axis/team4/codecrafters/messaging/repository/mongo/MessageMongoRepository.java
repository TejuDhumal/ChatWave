
package com.axis.team4.codecrafters.messaging.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.axis.team4.codecrafters.messaging.model.Message;
import java.util.List;

public interface MessageMongoRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<Message> findAll();
}
