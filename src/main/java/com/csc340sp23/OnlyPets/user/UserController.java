package com.csc340sp23.OnlyPets.user;

import com.csc340sp23.OnlyPets.post.Post;
import com.csc340sp23.OnlyPets.post.PostService;
import com.csc340sp23.OnlyPets.settings.Settings;
import com.csc340sp23.OnlyPets.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;
    
    @Autowired
    AdminUserController adminUserController;
    @Autowired
    PostService postService;

    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes re) throws UnirestException {

        // Redirect attributes is a wonderful thing
        // it allows me to use thymeleaf to set fields or even create alert modals

        // The way this works is if a user with the username or email is found, the site will redirect to register with
        // an alert and then because I already defined the email, username, and password fields the user doesn't have to
        // repopulate those fields manually, they just need to change their username or email accordingly
        // When it redirects to login, I've specified that the username field is to be automatically filled out but
        // I didn't do the same for password for some semblance of security even though it probably doesn't even matter
        // at the end of the day

        // getByUsername and getByEmail return null if user isn't found

        re.addFlashAttribute("email", user.getEmail());
        re.addFlashAttribute("username", user.getUsername());
        re.addFlashAttribute("password", user.getPassword());

        if(userService.getUserByUsername(user.getUsername()) != null) {
            re.addFlashAttribute("error", "A user with that username has already registered!");
            return "redirect:/register";
        }

        if(userService.getUserByEmail(user.getEmail()) != null) {
            re.addFlashAttribute("error", "A user with that email has already registered!");
            return "redirect:/register";
        }

        // Saves user
        // Detects if user is one of the devs

        String role = user.getUsername().equals("erin") ||
                user.getUsername().equals("andy") ||
                user.getUsername().equals("duncan") ?
                "ADMIN" : "USER";

        user.setAvatar("/assets/avatars/default.png");
        user.setRole(role);
        user.set_email_verified(false);
        userService.save(user);

        //temporary for testing purposes
        if(user.getUsername().equals("modmepls")) adminUserController.hireMod(user);

        // Creates user settings and saves
        Settings setting = new Settings(user.id);

        settingsService.save(setting);

        String code = user.getUsername() + "&code=";
        String options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for(int i = 0; i < 50; i++) {
            int rand = (int) Math.floor(Math.random() * options.length());
            code += options.substring(rand, rand + 1);
        }

        HttpResponse<JsonNode> request = Unirest
                .post("https://api.mailgun.net/v3/sandbox9873cf9eca034b4884e2a7048cb9b7c8.mailgun.org/messages")
			.basicAuth("api", "181449aa-839757f8")
                .queryString("from", "no-reply@onlypets.com")
                .queryString("to", user.getEmail())
                .queryString("subject", "Verify your OnlyPets Account")
                .queryString("text", "Verify your OnlyPets account by clicking the following link: " +
                        "http://127.0.0.1:8081/verify/"+code)
                .asJson();

        user.setVerificationCode(code);

        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }

    // Probs not the only issue, just what I noticed while adding my own code. Needs to be GetMapping
    @PostMapping("/dashboard")
    public String dashboardPage(Model model){
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "dashboard";
    }

    @PostMapping("/verify/{code}")
    public String verifyAccount() {
        return "redirect:/";
    }
}
