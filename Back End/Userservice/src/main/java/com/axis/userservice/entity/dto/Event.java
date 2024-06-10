package com.axis.userservice.entity.dto;

import java.util.List;

import com.axis.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private String subject;
	private List<User> result;
}
