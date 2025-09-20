package com.buki.twitchbot.dto;

import java.time.LocalDateTime;

public class UsernameDTO {
    private String username;
    private boolean isAvailable;
    private LocalDateTime lastChecked;

    public UsernameDTO(String username, boolean isAvailable, LocalDateTime now) {
    }

    public LocalDateTime getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
