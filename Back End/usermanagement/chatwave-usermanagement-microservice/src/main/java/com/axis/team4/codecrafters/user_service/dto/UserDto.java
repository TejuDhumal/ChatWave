// User DTO class

package com.axis.team4.codecrafters.user_service.dto;

import java.util.Objects;

public class UserDto {
	
	private Integer id;
	private String full_name;
	private String username;
	private String email;
	private String profile_picture;
	private String bio;
	
	
	public UserDto() {
		// TODO Auto-generated constructor stub
	}

//Constructor for UserDto
	
	public UserDto(Integer id, String full_name, String username, String email, String profile_picture, String bio) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.username = username;
		this.email = email;
		this.profile_picture = profile_picture;
		this.bio = bio;
		
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
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, full_name, username, id, profile_picture, bio);
	}

	// Overriding equals method
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(full_name, other.full_name) && Objects.equals(username, other.username)
				&& Objects.equals(id, other.id) && Objects.equals(profile_picture, other.profile_picture) && Objects.equals(bio, other.bio);
	}
}
