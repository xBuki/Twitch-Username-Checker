package com.buki.twitchbot.service;

import ch.qos.logback.core.subst.Token;
import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class TwitchUsernameChecker {
    private final IProductionSwitch productionSwitch;
    private final TokenRefresher tokenRefresher;
    private final OkHttpClient client = new OkHttpClient();

    public TwitchUsernameChecker(IProductionSwitch productionSwitch, TokenRefresher tokenRefresher) {
        this.productionSwitch = productionSwitch;
        this.tokenRefresher = tokenRefresher;
    }

    public boolean isUserNameAvailable(String username) throws IOException {
        String accessToken = tokenRefresher.getAccessToken();

        Request request = new Request.Builder()
                .url("https://api.twitch.tv/helix/users?login=" + username)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Client-Id", productionSwitch.getTwitchClientID())
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        return !body.contains("\"id\"");
    }
}
