package com.buki.twitchbot;

import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import com.buki.twitchbot.resources.eventlisteners.OnReadyEvent;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.springframework.stereotype.Component;

@Component
public class ShardsManager {
    private final IProductionSwitch productionSwitch;
    private final OnReadyEvent onReadyEvent;

    public ShardsManager(IProductionSwitch productionSwitch, OnReadyEvent onReadyEvent) {
        this.productionSwitch = productionSwitch;
        this.onReadyEvent = onReadyEvent;
    }

    public void buildShards() {
        DefaultShardManagerBuilder shardManagerBuilder;
        shardManagerBuilder = DefaultShardManagerBuilder.createLight(productionSwitch.getDiscordToken());
        shardManagerBuilder.setStatus(OnlineStatus.ONLINE);
        shardManagerBuilder.setActivity(Activity.watching(productionSwitch.getStatus()));
        shardManagerBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.MESSAGE_CONTENT);
        shardManagerBuilder.addEventListeners(onReadyEvent);
        shardManagerBuilder.build();
    }
}
