package com.buki.twitchbot;

import com.buki.twitchbot.config.TwitchConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@Service
public class TwitchService {

    private static final Logger log = LoggerFactory.getLogger(TwitchService.class);
    private static final String TOKEN_URL = "https://id.twitch.tv/oauth2/token";
    private static final String USERS_URL = "https://api.twitch.tv/helix/users?login=";

    private final TwitchConfig config;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private String accessToken;
    private Instant tokenExpiry = Instant.EPOCH;

    public TwitchService(TwitchConfig config) {
        this.config = config;
    }

    public boolean isUsernameAvailable(String username) throws Exception {
        ensureValidToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URL + username))
                .GET()
                .header("Client-Id", config.getClientId())
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            // Token was revoked externally — refresh and retry once
            log.warn("Twitch token rejected (401), refreshing...");
            refreshToken();
            return isUsernameAvailable(username);
        }

        if (response.statusCode() != 200) {
            throw new RuntimeException("Twitch API returned HTTP " + response.statusCode() + ": " + response.body());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray data = json.getAsJsonArray("data");
        return data.isEmpty();
    }

    private void ensureValidToken() throws Exception {
        // Refresh 5 minutes before expiry to avoid mid-cycle failures
        if (Instant.now().isAfter(tokenExpiry.minusSeconds(300))) {
            refreshToken();
        }
    }

    private void refreshToken() throws Exception {
        log.info("Fetching new Twitch app access token...");

        String body = "client_id=" + config.getClientId()
                + "&client_secret=" + config.getClientSecret()
                + "&grant_type=client_credentials";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to obtain Twitch token: HTTP " + response.statusCode() + ": " + response.body());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        accessToken = json.get("access_token").getAsString();
        int expiresIn = json.get("expires_in").getAsInt();
        tokenExpiry = Instant.now().plusSeconds(expiresIn);

        log.info("Twitch token obtained (expires in {} seconds).", expiresIn);
    }
}
