package com.axis.ChatWave.dtos;

public class GroupCreationRequest {
    private Long userId;
    private String name;

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

