package com.buki.twitchbot.configuration.interfaces;

import java.math.BigInteger;

public interface IProductionSwitch {
    String getDiscordToken();
    String getTwitchClientID();
    String getTwitchClientSecret();
    String getTwitchAccessToken();
    String[] getTwitchUsernames();
    BigInteger getUserID();
    String getStatus();
}
