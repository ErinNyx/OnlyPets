package com.csc340sp23.OnlyPets.user;

import com.csc340sp23.OnlyPets.post.PostService;
import com.csc340sp23.OnlyPets.settings.Settings;
import com.csc340sp23.OnlyPets.settings.SettingsService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

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

        //temporary for testing purposes
        if(user.getUsername().equals("modmepls")) adminUserController.hireMod(user.getUsername());

        // Creates user settings and saves
        Settings setting = new Settings(user.id);

        settingsService.save(setting);

        // Send Email
        String from = "no-reply@onlypets.com";
        String to = user.getEmail();

        String subject = "Please verify your OnlyPets Account";

        String options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String code = user.getUsername() + "&code=";

        for(int i = 0; i < 5; i++) {
            int rand = (int) Math.floor(Math.random() * options.length());
            code += options.substring(rand, rand+1);
        }

        user.setCode(code);
        userService.save(user);

        String content = "Hello " + user.getUsername() + ", \n" +
                "We're happy to see you join OnlyPets! One thing before you log in, please verify your account by clicking this link: " +
                "http://127.0.0.1:8081/verify/" + code;

        HttpResponse<JsonNode> request = Unirest
                .post("https://api.mailgun.net/v3/sandbox9873cf9eca034b4884e2a7048cb9b7c8.mailgun.org/messages")
                .basicAuth("api", "89eccf64199ed21e3d733a965f75be85-181449aa-839757f8")
                .queryString("from", from)
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", content)
                .asJson();
        System.out.println(request.getBody());

        re.addFlashAttribute("error", "Account registered! Please note that you cannot log in until you verify your email address." +
                "A meesage has been sent to your email.");

        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }
    

    @PostMapping("/dashboard")
    public String dashboardPage(Model model){
        List<User> modList = userService.getUsersByRole();
        model.addAttribute("modList", modList);
        return "dashboard";
    }


    @GetMapping("/verify/{pathVar}")
    public String verifyEmail(@PathVariable String pathVar, RedirectAttributes re) {
        String[] options = pathVar.split("&code=");
        String username = options[0];
        String code = pathVar;

        User user = userService.getUserByUsername(username);

        if(user == null)
            re.addFlashAttribute("error", "No user found!");

        if(user.getCode().equals(code)) {
            user.setVerified(true);
            userService.save(user);
            re.addFlashAttribute("error", "Success, you may now log in!");
        } else
            re.addFlashAttribute("error", "Invalid code!");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginError(@RequestParam(value = "error", required = false) boolean error, RedirectAttributes re) {
        if(error) {
            re.addFlashAttribute("error", "Invalid username or password!");
            return "redirect:/login";
        }

        return "login";
    }
}
