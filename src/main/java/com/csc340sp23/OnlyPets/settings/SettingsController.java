package com.csc340sp23.OnlyPets.settings;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SettingsController {
    @Autowired
    SettingsService settings;

    @GetMapping("/settings/id={userId}")
    public String settingsPage(@PathVariable int userId, Model model) {
        model.addAttribute("settings", settings.getSettingsByUserId(userId));
        return "settings-page";
    }
}
