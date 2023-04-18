package com.csc340sp23.OnlyPets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class AdminUserController {
    @Autowired UserService userService;

    @PostMapping("/hire")
    public String hireMod(User user){
        if(!userService.getUserByUsername(user.getUsername()).getRole().equalsIgnoreCase("MOD")){
            System.out.println("User role after registration: " + user.getRole());
            user.setRole("MOD");
            userService.save(user);
            System.out.println("User role after fireMod(): " + user.getRole());
        }
        return "hire";

    }
    @PostMapping("/fire")
    public String fireMod(User user){
        if(userService.getUserByUsername(user.getUsername()).getRole().equalsIgnoreCase("MOD")){
            System.out.println("User role after registration: " + user.getRole());
            user.setRole("USER");
            userService.save(user);
            System.out.println("User role after fireMod(): " + user.getRole());
        }
        return "dashboard";

    }
}
