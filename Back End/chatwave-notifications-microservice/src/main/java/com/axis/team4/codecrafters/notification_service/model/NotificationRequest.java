// The NotificationRequest model class is used to store the request data for sending notifications.

package com.axis.team4.codecrafters.notification_service.model;

public class NotificationRequest {
    private Integer chatId;
    private Integer userId;
    private String message;

    public NotificationRequest() {}

    public NotificationRequest(Integer chatId, Integer userId, String message) {
        this.chatId = chatId;
        this.setUserId(userId);
        this.message = message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
