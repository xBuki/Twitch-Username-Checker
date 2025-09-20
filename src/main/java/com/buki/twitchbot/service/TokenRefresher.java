package com.buki.twitchbot.service;

import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class TokenRefresher {
    private final IProductionSwitch productionSwitch;
    private String accessToken;
    private int refreshAttempts;
    private long expirationTime;

    public TokenRefresher(IProductionSwitch productionSwitch) {
        this.productionSwitch = productionSwitch;
    }

    public String getAccessToken() throws IOException {
        if (accessToken == null || System.currentTimeMillis() >= expirationTime) refreshAccessToken();
        return accessToken;
    }

    public void refreshAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("client_id", productionSwitch.getTwitchClientID())
                .add("client_secret", productionSwitch.getTwitchClientSecret())
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("https://id.twitch.tv/oauth2/token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            accessToken = jsonNode.get("access_token").asText();
            expirationTime = System.currentTimeMillis() + jsonNode.get("expires_in").asLong() * 1000;
            refreshAttempts++;
        }
    }

    public int getRefreshAttempts() {
        return refreshAttempts;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
