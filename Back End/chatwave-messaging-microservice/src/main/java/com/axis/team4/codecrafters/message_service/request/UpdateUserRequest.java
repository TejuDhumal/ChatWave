// Purpose: UpdateUserRequest class for handling request for updating user details.

package com.axis.team4.codecrafters.message_service.request;

public class UpdateUserRequest {
	
	private String full_name;
	private String profile_picture;
	
	public UpdateUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public UpdateUserRequest(String full_name, String profile_picture) {
		super();
		this.full_name = full_name;
		this.profile_picture = profile_picture;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}
	

}
