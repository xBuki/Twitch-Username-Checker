package com.buki.twitchbot.storage;

import com.buki.twitchbot.dto.UsernameDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class UsernameStatusStore {
    private final Map<String, UsernameDTO> usernameMap = new ConcurrentHashMap<>();

    public void updateStatus(String username, boolean isAvailable) {
        usernameMap.put(username, new UsernameDTO(username, isAvailable, LocalDateTime.now()));
    }

    public Collection<UsernameDTO> getAllStatuses() {
        return usernameMap.values();
    }
}
