package com.csc340sp23.OnlyPets.past;

import com.csc340sp23.OnlyPets.past.Post;
import com.csc340sp23.OnlyPets.past.PostService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PostController {
    @Autowired
    PostService postService;

    // the picture is hardcoded because I need to work on the front end to submit pictures
    @PostMapping("/post")
    public String post(Post post) {

        post.setFlagged(false);
        post.setPicture("/assets/pets/e1.jpg");
        post.setLikes(0);
        post.setDislikes(0);
        post.setReports(0);
        post.setAuthorName("test");

        post.setCreated_at((int) System.currentTimeMillis());

        postService.save(post);
        return "redirect:/";
    }
}