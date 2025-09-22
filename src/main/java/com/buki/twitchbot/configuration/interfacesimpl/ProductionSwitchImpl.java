package com.buki.twitchbot.configuration.interfacesimpl;

import com.buki.twitchbot.configuration.EnvConfig;
import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import com.buki.twitchbot.functions.interfaces.ITextParser;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ProductionSwitchImpl implements IProductionSwitch {
    private final ITextParser textParser;

    public ProductionSwitchImpl(ITextParser textParser) {
        this.textParser = textParser;
    }

    @Override
    public String getDiscordToken() {
        return EnvConfig.get("DISCORD_TOKEN");
    }

    @Override
    public String getTwitchClientID() {
        return EnvConfig.get("TWITCH_CLIENT_ID");
    }

    @Override
    public String getTwitchClientSecret() {
        return EnvConfig.get("TWITCH_CLIENT_SECRET");
    }

    @Override
    public String getTwitchAccessToken() {
        return EnvConfig.get("ACCESS_TOKEN");
    }

    @Override
    public String[] getTwitchUsernames() {
        return EnvConfig.get("TWITCH_USERNAMES").split(",");
    }

    @Override
    public BigInteger getUserID() {
        return textParser.parseToBigInt(EnvConfig.get("DISCORD_USER_ID"));
    }

    @Override
    public String getStatus() {
        return "Stalking some usernames! :)";
    }
}
