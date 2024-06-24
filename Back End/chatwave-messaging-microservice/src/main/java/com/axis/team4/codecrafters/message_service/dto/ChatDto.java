// Purpose: ChatDto class to store the chat details.

package com.axis.team4.codecrafters.message_service.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.axis.team4.codecrafters.message_service.modal.User;

import jakarta.persistence.Column;


public class ChatDto {

	private Integer id;
	private String chat_name;
	private String chat_image;
	private Boolean blocked=false;
	private Integer blocked_by;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean is_group;
	
	private Set<UserDto> admins= new HashSet<>();
	
	private UserDto created_by;

	private Set<UserDto> users = new HashSet<>();
	
	private List<MessageDto> messages=new ArrayList<>();
	
	User user = new User();

	public ChatDto() {
		// TODO Auto-generated constructor stub
	}


	public ChatDto(Integer id, String chat_name, String chat_image, Boolean is_group, Set<UserDto> admins,
			UserDto created_by, Set<UserDto> users, List<MessageDto> messages, boolean blocked, Integer blocked_by) {
		super();
		this.id = id;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
		this.is_group = is_group;
		this.admins = admins;
		this.created_by = created_by;
		this.users = users;
		this.messages = messages;
		this.blocked = false;
		this.setBlocked_by(blocked_by);
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Set<UserDto> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<UserDto> admins) {
		this.admins = admins;
	}

	public List<MessageDto> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageDto> messages) {
		this.messages = messages;
	}
	public String getChat_name() {
		return chat_name;
	}

	public void setChat_name(String chat_name) {
		this.chat_name = chat_name;
	}

	public String getChat_image() {
		return chat_image;
	}

	public void setChat_image(String chat_image) {
		this.chat_image = chat_image;
	}

	public Boolean getIs_group() {
		return is_group;
	}

	public void setIs_group(Boolean is_group) {
		this.is_group = is_group;
	}

	public UserDto getCreated_by() {
		return created_by;
	}

	public void setCreated_by(UserDto created_by) {
		this.created_by = created_by;
	}

	public Set<UserDto> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDto> users) {
		this.users = users;
	}
	
	public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


	public Integer getBlocked_by() {		
		return blocked_by;
	}


	public void setBlocked_by(Integer blocked_by) {
		this.blocked_by = blocked_by;
	}
    
	
}