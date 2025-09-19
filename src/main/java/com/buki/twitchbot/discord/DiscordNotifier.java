package com.buki.twitchbot.discord;

import com.buki.twitchbot.configuration.interfaces.IProductionSwitch;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

@Service
public class DiscordNotifier {
    private final IProductionSwitch productionSwitch;
    private JDA jda;

    public DiscordNotifier(IProductionSwitch productionSwitch) {
        this.productionSwitch = productionSwitch;
    }

    @PostConstruct
    public void init() throws InterruptedException {
        jda = JDABuilder.createDefault(productionSwitch.getDiscordToken()).build();
        jda.awaitReady();
    }

    public void messageUser(String message) {
        User user = jda.getUserById(productionSwitch.getUserID().toString());
        if (user != null) {
            user.openPrivateChannel().queue(channel -> {
                channel.sendMessage(message).queue();
            });
        }

    }


}
