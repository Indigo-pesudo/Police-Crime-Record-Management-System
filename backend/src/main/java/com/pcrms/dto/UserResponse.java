package com.pcrms.dto;

import com.pcrms.model.User;

public class UserResponse {

    private Long id;
    private String username;
    private String fullName;
    private User.Role role;
    private boolean active;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String fullName,
                        User.Role role, boolean active) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public User.Role getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }
}