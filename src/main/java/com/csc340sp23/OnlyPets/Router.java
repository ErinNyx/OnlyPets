package com.csc340sp23.OnlyPets;

import com.csc340sp23.OnlyPets.past.PostService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Temporary Routing file for Project Demo
@Controller
@RequestMapping("/")

public class Router {
    @Autowired
    PostService postService;

    @GetMapping()
    public String defaultHomePage(Model model) {
        model.addAttribute("postList", postService.getAllPosts());
        return "home";
    }

    @GetMapping("/home-admin")
    public String adminHomePage() {
        return "home-admin";
    }


    @GetMapping("/home-user")
    public String userHomePage() {
        return "home-user";
    }

    @GetMapping("/home-moderator")
    public String modHomePage() {
        return "home-moderator";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/password-reset")
    public String passwordResetPage() {
        return "password-reset";
    }

    @GetMapping("/admin-hired")
    public String adminHiredPage() {
        return "admin-hired";
    }
}