package com.csc340sp23.OnlyPets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminUserController {
    @Autowired
    UserService userService;

    @PostMapping("/hire")
    public String hireMod(String userName) {
        User user = userService.getUserByUsername(userName);
        if (user == null) return "redirect:/dashboard";
        if (!user.getRole().equalsIgnoreCase("MOD")) {

            user.setRole("MOD");
            userService.save(user);

        }
        return "redirect:/dashboard";

    }

    @PostMapping("/fire")
    public String fireMod(String userName) {
        User user = userService.getUserByUsername(userName);
        if (user == null) return "redirect:/dashboard";
        if (user.getRole().equalsIgnoreCase("MOD")) {

            user.setRole("USER");
            userService.save(user);

        }
        return "redirect:/dashboard";

    }


}
