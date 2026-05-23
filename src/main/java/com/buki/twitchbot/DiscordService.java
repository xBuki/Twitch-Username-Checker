package com.buki.twitchbot;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class DiscordService {

    private static final Logger log = LoggerFactory.getLogger(DiscordService.class);
    private static final Gson GSON = new Gson();

    @Value("${discord.webhook-url}")
    private String webhookUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void sendNotification(String message) {
        try {
            String payload = GSON.toJson(Map.of("content", message));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                log.info("Discord notification sent.");
            } else {
                log.error("Discord webhook returned HTTP {}: {}", response.statusCode(), response.body());
            }
        } catch (Exception e) {
            log.error("Failed to send Discord notification", e);
        }
    }
}
