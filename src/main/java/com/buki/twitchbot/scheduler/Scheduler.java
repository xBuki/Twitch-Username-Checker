package com.buki.twitchbot.scheduler;

import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import com.buki.twitchbot.discord.DiscordNotifier;
import com.buki.twitchbot.service.TokenRefresher;
import com.buki.twitchbot.service.TwitchUsernameChecker;
import okhttp3.OkHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Scheduler {
    private final IProductionSwitch productionSwitch;
    private final TwitchUsernameChecker checker;
    private final DiscordNotifier discordNotifier;

    public Scheduler(IProductionSwitch productionSwitch, TwitchUsernameChecker checker, DiscordNotifier discordNotifier) {
        this.productionSwitch = productionSwitch;
        this.checker = checker;
        this.discordNotifier = discordNotifier;
    }

    @Scheduled(fixedRate = 180_000)
    public void checkUsername() {
        for (String username : productionSwitch.getTwitchUsernames()) {
            try {
                boolean isAvailable = checker.isUserNameAvailable(username.trim());
                if (isAvailable) {
                    discordNotifier.messageUser("The following name is available: " + username + ".");
                }
            } catch (Exception e) {
                discordNotifier.messageUser("Error checking, check console: "+ e.getMessage());
            }
        }
    }

}
