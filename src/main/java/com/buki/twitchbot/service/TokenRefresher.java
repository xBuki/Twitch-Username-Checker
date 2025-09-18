package com.buki.twitchbot.service;

import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import okhttp3.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class TokenRefresher {
    private final IProductionSwitch productionSwitch;
    private String accessToken;

    public TokenRefresher(IProductionSwitch productionSwitch) {
        this.productionSwitch = productionSwitch;
    }

    public String getAccessToken() throws IOException {
        if (accessToken == null) refreshAccessToken();
        return accessToken;
    }

    public void refreshAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("client_id", productionSwitch.getTwitchClientID())
                .add("client_secret", productionSwitch.getTwitchClientSecret())
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("https://id.twitch.tv/oauth2/token")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        //JSONObject json = new JSONObject(responseBody);
        //accessToken = json.getString("access_token").toString();
    }
}
