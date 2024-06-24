// Purpose: Contains the MessageRepositoryRedis interface.

package com.axis.team4.codecrafters.message_service.repository.redis;

import com.axis.team4.codecrafters.message_service.modal.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepositoryRedis extends CrudRepository<Message, Integer> {
}
