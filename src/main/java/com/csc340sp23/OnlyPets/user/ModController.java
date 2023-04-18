package com.csc340sp23.OnlyPets.user;

import com.csc340sp23.OnlyPets.settings.Settings;
import com.csc340sp23.OnlyPets.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")

/**
 *
 * @author unclu
 */
public class ModController {

    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;

    @PostMapping("/timeout")
    public String timeoutUser(String username, int duration){

        // Gets start time for timeout period.
        long date = System.currentTimeMillis();
        
        // Gets user to be timed out by username.
        User user = userService.getUserByUsername(username);
        
        // Converts timeout duration from hours to milliseconds.
        long timeMillis = duration * 3600000;

        long timeout = timeMillis + date;

        user.setTimedout(timeout);

        userService.save(user);

        return "redirect:/";
    }
    
    @GetMapping("/timeout")
    public String timeoutPage(Model model){
        return "timeout";
    }
    
}
