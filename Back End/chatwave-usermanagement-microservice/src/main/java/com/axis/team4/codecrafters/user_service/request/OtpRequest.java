// Overview: This file contains the request object for the OTP verification.

package com.axis.team4.codecrafters.user_service.request;

public class OtpRequest {
  private String email;
  private String otp;
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
}
