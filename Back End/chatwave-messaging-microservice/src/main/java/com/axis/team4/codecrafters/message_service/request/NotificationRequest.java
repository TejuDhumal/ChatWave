// Puurpose: Contains the NotificationRequest class which is used to send a notification to a user.

package com.axis.team4.codecrafters.message_service.request;

public class NotificationRequest {
    private Integer chatId;
    private String message;
    private Integer userId;

    public NotificationRequest() {}

    public NotificationRequest(Integer chatId, Integer userId, String message) {
        this.chatId = chatId;
        this.userId = userId;
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
