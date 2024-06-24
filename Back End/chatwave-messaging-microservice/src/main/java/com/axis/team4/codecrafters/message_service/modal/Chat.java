// This is a modal class for chat which contains all the attributes of chat and their getter and setter methods.

package com.axis.team4.codecrafters.message_service.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.axis.team4.codecrafters.message_service.dto.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Chats")
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String chat_name;
	private String chat_image;
	@Column(name = "blocked", nullable = false)
    private boolean blocked=false;
	private Integer blocked_by;

	@ManyToMany
	private Set<User> admins = new HashSet<>();

	private Boolean is_group;

	@ManyToOne
	private User created_by;

	@ManyToMany
	@JoinTable(name = "chat_users", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@OneToMany
	private List<Message> messages = new ArrayList<>();

	public Chat() {

	}

	public Chat(Integer id, String chat_name, String chat_image, Set<User> admins, Boolean is_group, User created_by,
			Set<User> users, List<Message> messages, boolean blocked, Integer blocked_by) {
		super();
		this.id = id;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
		this.admins = admins;
		this.is_group = is_group;
		this.created_by = created_by;
		this.users = users;
		this.messages = messages;
		this.blocked = blocked;
		this.blocked_by = blocked_by;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<User> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<User> admins) {
		this.admins = admins;
	}

	public Boolean getIs_group() {
		return is_group;
	}

	public void setIs_group(Boolean is_group) {
		this.is_group = is_group;
	}

	public User getCreated_by() {
		return created_by;
	}

	public void setCreated_by(User created_by) {
		this.created_by = created_by;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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

	@Override
	public String toString() {
		return "Chat [id=" + id + ", chat_name=" + chat_name + ", chat_image=" + chat_image + ", admins=" + admins
				+ ", is_group=" + is_group + ", created_by=" + created_by + ", users=" + users + ", messages="
				+ messages + ", blocked=" + blocked + ", blocked_by=" + blocked_by + "]";
	}
}
