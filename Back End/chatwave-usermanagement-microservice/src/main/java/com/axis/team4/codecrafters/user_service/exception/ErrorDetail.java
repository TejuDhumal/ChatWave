// Exception class to handle error details

package com.axis.team4.codecrafters.user_service.exception;

import java.time.LocalDateTime;

public class ErrorDetail {
	
	private String error;
	private String detail;
	private LocalDateTime timestamp;
	
	public ErrorDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public ErrorDetail(String error, String detail, LocalDateTime timestamp) {
		super();
		this.error = error;
		this.detail = detail;
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	

}
