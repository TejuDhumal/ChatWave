// Purpose: SingleChatRequest class to get userId from the user.

package com.axis.team4.codecrafters.message_service.request;

public class SingleChatRequest {
	
	
	private Integer userId;

	public SingleChatRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public SingleChatRequest(Integer userId) {
		super();
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

}
