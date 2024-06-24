// Auth Response class for the User Service

package com.axis.team4.codecrafters.user_service.response;

public class AuthResponse {
  private String jwt;
  private boolean status;
  private String message; // Add this field

  public AuthResponse() {
    // Default constructor
  }

  public AuthResponse(String jwt, boolean status, String message) {
    super();
    this.jwt = jwt;
    this.status = status;
    this.message = message;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
