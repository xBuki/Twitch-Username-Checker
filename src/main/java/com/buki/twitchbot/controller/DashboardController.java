package com.buki.twitchbot.controller;

import com.buki.twitchbot.service.TokenRefresher;
import com.buki.twitchbot.storage.UsernameStatusStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {
    private final UsernameStatusStore usernameStatusStore;
    private final TokenRefresher tokenRefresher;

    public DashboardController(UsernameStatusStore usernameStatusStore, TokenRefresher tokenRefresher) {
        this.usernameStatusStore = usernameStatusStore;
        this.tokenRefresher = tokenRefresher;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("statuses", usernameStatusStore.getAllStatuses());
        model.addAttribute("refreshAttempts", tokenRefresher.getRefreshAttempts());
        model.addAttribute("tokenExpiry", tokenRefresher.getExpirationTime());
        return "dashboard";
    }
}
