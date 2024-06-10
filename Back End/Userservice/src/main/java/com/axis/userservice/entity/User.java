package com.axis.userservice.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.axis.userservice.entity.dto.Requests;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "email")
	private String email;

	private String password;
	
	@Column(name = "mobile_no")
	private long mobile_no;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "is_Active")
	private Boolean isActive;
	
	@Column(name = "verify_status")
	private boolean verifyStatus;
	
	@Column(name = "user_status")
	private String userStatus;
	
	@Column(name = "bio")
	private String bio;
	
	@Column(name = "profile_photo")
	private String profilePhoto;
	
	@Column(name = "created_at")
	private Date created_at;
	
	@Column(name = "updated_at")
	private Date updated_at;

	private String verificationToken;

	public User() {
		this.verificationToken = UUID.randomUUID().toString();
	}
	
	
	
}
