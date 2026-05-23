package com.buki.twitchbot;

import com.buki.twitchbot.config.TwitchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UsernameCheckerScheduler {

    private static final Logger log = LoggerFactory.getLogger(UsernameCheckerScheduler.class);

    private final TwitchService twitchService;
    private final DiscordService discordService;
    private final TwitchConfig twitchConfig;

    // Tracks last known state so we only alert on transitions, not every check
    private final Map<String, Boolean> lastKnownState = new ConcurrentHashMap<>();

    public UsernameCheckerScheduler(TwitchService twitchService, DiscordService discordService, TwitchConfig twitchConfig) {
        this.twitchService = twitchService;
        this.discordService = discordService;
        this.twitchConfig = twitchConfig;
    }

    @Scheduled(initialDelay = 5_000, fixedDelayString = "${checker.interval-ms:300000}")
    public void checkUsernames() {
        log.info("Checking {} username(s)...", twitchConfig.getUsernames().size());

        for (String username : twitchConfig.getUsernames()) {
            try {
                boolean available = twitchService.isUsernameAvailable(username);
                Boolean previous = lastKnownState.put(username, available);

                if (available && !Boolean.TRUE.equals(previous)) {
                    log.info("'{}' is AVAILABLE!", username);
                    discordService.sendNotification(
                            "**Twitch username available!** `" + username + "` is free to claim!\n" +
                            "https://www.twitch.tv/" + username
                    );
                } else if (!available && Boolean.TRUE.equals(previous)) {
                    log.info("'{}' has been taken again.", username);
                } else {
                    log.debug("'{}' is {}.", username, available ? "available" : "taken");
                }
            } catch (Exception e) {
                log.error("Error checking username '{}'", username, e);
            }
        }
    }
}
