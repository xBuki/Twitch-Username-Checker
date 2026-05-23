package com.buki.twitchbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "twitch")
public class TwitchConfig {

    private String clientId;
    private String clientSecret;
    private List<String> usernames = new ArrayList<>();

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public List<String> getUsernames() { return usernames; }
    public void setUsernames(List<String> usernames) { this.usernames = usernames; }
}
