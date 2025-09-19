package com.buki.twitchbot.configuration.interfaces;

import java.math.BigInteger;

public interface IProductionSwitch {
    String getDiscordToken();
    String getTwitchClientID();
    String getTwitchClientSecret();
    String[] getTwitchUsernames();
    BigInteger getUserID();
    String getStatus();
}
