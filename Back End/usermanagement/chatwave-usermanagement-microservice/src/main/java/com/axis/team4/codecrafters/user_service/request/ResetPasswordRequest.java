// Request class for reset password

package com.axis.team4.codecrafters.user_service.request;

public class ResetPasswordRequest {
  private String email;
  private String otp;
  private String newPassword;
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getOtp() {
    return otp;
  }
  public void setOtp(String otp) {
    this.otp = otp;
  }
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  // getters and setters
}
