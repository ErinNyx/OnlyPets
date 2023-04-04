package com.csc340sp23.OnlyPets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home-admin")
public class AdminUserController {
    @Autowired UserService userService;

    @PostMapping
    public String isAdmin(User user, RedirectAttributes re){
        if(!userService.getUserById(user.getId()).getRole().equalsIgnoreCase("ADMIN")){
            re.addFlashAttribute("error", "You do not have admin access rights!");
            return "redirect:/login";
        }
        return "redirect:/admin-hired";
    }

    @PostMapping
    public String hireMod(User user, RedirectAttributes re){
        if(!userService.getUserById(user.getId()).getRole().equalsIgnoreCase("MOD")){
            user.setRole("MOD");
            userService.save(user);
        }
        return "redirect:/admin-hired";
    }


}
