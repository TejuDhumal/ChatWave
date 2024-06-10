package com.axis.userservice.entity.dto;

import java.util.List;

import com.axis.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUser {
	private String subject;
	private User result;
}
