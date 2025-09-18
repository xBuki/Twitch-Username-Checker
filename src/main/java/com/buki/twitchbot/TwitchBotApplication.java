package com.buki.twitchbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwitchBotApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TwitchBotApplication.class, args);
        ShardsManager shardsManager = context.getBean(ShardsManager.class);
        shardsManager.buildShards();
    }
}
