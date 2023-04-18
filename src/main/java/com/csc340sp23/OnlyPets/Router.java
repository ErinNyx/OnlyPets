package com.csc340sp23.OnlyPets;

import com.csc340sp23.OnlyPets.post.PostService;
import com.csc340sp23.OnlyPets.ratings.RatingService;
import com.csc340sp23.OnlyPets.user.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Temporary Routing file for Project Demo
@Controller
@RequestMapping("/")

public class Router {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    @GetMapping()
    public String defaultHomePage(Model model) {
        model.addAttribute("postList", postService.getAllPosts());
        model.addAttribute("userService", userService);
        model.addAttribute("postService", postService);
        model.addAttribute("ratingService", ratingService);
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

    @GetMapping("/admin-hired")
    public String adminHiredPage() {
        return "admin-hired";
    }
}