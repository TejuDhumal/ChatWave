// Notification Modal class

package com.axis.team4.codecrafters.user_service.modal;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String message;
	private Boolean is_seen;
	private LocalDateTime timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Notification() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Notification(Integer id, String message, Boolean is_seen, LocalDateTime timestamp, User user) {
		super();
		this.id = id;
		this.message = message;
		this.is_seen = is_seen;
		this.timestamp = timestamp;
		this.user = user;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIs_seen() {
		return is_seen;
	}

	public void setIs_seen(Boolean is_seen) {
		this.is_seen = is_seen;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
