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
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;
    
    @Autowired
    AdminUserController adminUserController;

    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes re) {

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

        user.setRole(role);
        userService.save(user);


        //temporary for testing purposes
        if(user.getUsername().equals("modmepls")) adminUserController.hireMod(user);

        // Creates user settings and saves
        
        Settings setting = new Settings(user.id);

        settingsService.save(setting);

        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }

    // Was testing to see if Authentication was working. Turns out, even if the user has USER as role, they can still
    // call the /admin/test url, it just shows as 404.
    // Even though if you're not authenticated at all you get redirected
    // to login which was the expected behaviour for a USER trying to access ADMIN resources but ig I was wrong on that
    // assumption. Don't assume things kids, I spent like 12 hours on this.

    /*@GetMapping("/admin/test")
    public String authTest(Model model) {
        return "temporary-admin-test-for-auth";
    }*/
}
