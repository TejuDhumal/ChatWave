package com.axis.team4.codecrafters.chatwave.notification_service.repository;

import com.axis.team4.codecrafters.chatwave.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
