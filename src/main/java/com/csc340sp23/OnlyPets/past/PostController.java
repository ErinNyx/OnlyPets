package com.csc340sp23.OnlyPets.past;

import com.csc340sp23.OnlyPets.past.Post;
import com.csc340sp23.OnlyPets.past.PostService;
import com.csc340sp23.OnlyPets.user.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    // the picture is hardcoded because I need to work on the front end to submit pictures
    @PostMapping("/post")
    public String post(@RequestParam("image") MultipartFile file, Post post, RedirectAttributes redirectAttributes) throws IOException {

        if(post.title.length() > 20) {
            redirectAttributes.addFlashAttribute("error", "The title length is too long!");
            return "redirect:/";
        }

        if(userService.getUserByUsername(post.author) == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid author");
            return "redirect:/";
        }

        post.setFlagged(false);
        post.setLikes(0);
        post.setDislikes(0);
        post.setReports(0);

        post.setCreated_at((int) System.currentTimeMillis());

        postService.save(post);

        // Yes. I know this would probably make more sense to run this BEFORE the post was saved the first time.
        // I promise I'm not stupid.
        // But, for whatever godforsaken reason, the Post object doesn't update the id before you save it,
        // So here this block stays, and it will stay here until the end of time.

        if(file != null) {
            String path = "target/classes/static/assets/pets";
            String newFile = new File(path).getAbsolutePath() + "/" + post.getId() + ".jpg";
            try {
                FileOutputStream fout = new FileOutputStream(newFile);
                fout.write(file.getBytes());
                fout.close();

                post.setPicture("/assets/pets/" + post.getId() + ".jpg");
                postService.save(post);
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "A critical error has occurred. " +
                        "Please try again later. If the issue persists, please contact the webmaster at d_argo@uncg.edu");
                return "redirect:/";
            }
        }

        return "redirect:/";
    }
}