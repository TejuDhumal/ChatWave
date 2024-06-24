// Purpose: Contains the repository interface for the Chat entity in the MongoDB database.

package com.axis.team4.codecrafters.message_service.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.axis.team4.codecrafters.message_service.modal.Chat;

public interface ChatRepositoryMongo extends MongoRepository<Chat, Integer> {
}
