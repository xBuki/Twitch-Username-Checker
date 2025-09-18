package com.buki.twitchbot.resources.eventlisteners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OnReadyEvent extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OnReadyEvent.class);

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("{} is now Online!", event.getJDA().getSelfUser().getAsTag());
    }
}
