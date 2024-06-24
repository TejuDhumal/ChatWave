// Purpose: UserDto class to store user data.

package com.axis.team4.codecrafters.message_service.dto;

import java.util.Objects;

public class UserDto {
	
	private Integer id;
	private String full_name;
	private String email;
	private String profile_picture;
	private String username;
	private String bio;
	
	
	public UserDto() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDto(Integer id, String full_name, String email, String profile_picture, String username, String bio) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.email = email;
		this.profile_picture = profile_picture;
		this.setUsername(username);
		this.setBio(bio);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_pic(String profile_picture) {
		this.profile_picture = profile_picture;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(email, full_name, id, profile_picture);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(full_name, other.full_name)
				&& Objects.equals(id, other.id) && Objects.equals(profile_picture, other.profile_picture);
	}
	
	

}
