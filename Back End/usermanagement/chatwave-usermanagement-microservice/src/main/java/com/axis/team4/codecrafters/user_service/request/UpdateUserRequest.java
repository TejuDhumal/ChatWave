// Update user request handler

package com.axis.team4.codecrafters.user_service.request;

public class UpdateUserRequest {
  private String full_name;
  private String profile_picture;
  private String bio;

  public UpdateUserRequest() {
    // Default constructor
  }

  public UpdateUserRequest(
      String full_name, String profile_picture, String bio) {
    this.full_name = full_name;
    this.profile_picture = profile_picture;
    this.bio = bio;
  }

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public String getProfile_picture() {
    return profile_picture;
  }

  public void setProfile_picture(String profile_picture) {
    this.profile_picture = profile_picture;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }
}
