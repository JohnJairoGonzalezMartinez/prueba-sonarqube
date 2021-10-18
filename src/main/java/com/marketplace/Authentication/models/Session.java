package com.marketplace.Authentication.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Session {

    @Id
    private String token;
    private String userId;
    private LocalDateTime lastInteraction;

    public Session(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(LocalDateTime lastInteraction) {
        this.lastInteraction = lastInteraction;
    }
}
