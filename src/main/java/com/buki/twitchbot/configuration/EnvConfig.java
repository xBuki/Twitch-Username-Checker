package com.buki.twitchbot.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure().filename("secrets.env").load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
