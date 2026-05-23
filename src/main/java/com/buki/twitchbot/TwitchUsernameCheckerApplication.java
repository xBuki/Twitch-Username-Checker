package com.buki.twitchbot;

import com.buki.twitchbot.config.TwitchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(TwitchConfig.class)
public class TwitchUsernameCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitchUsernameCheckerApplication.class, args);
    }
}
