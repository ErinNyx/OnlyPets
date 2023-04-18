package com.csc340sp23.OnlyPets.post;

import com.csc340sp23.OnlyPets.ratings.Dislikes;
import com.csc340sp23.OnlyPets.ratings.Likes;
import com.csc340sp23.OnlyPets.ratings.RatingService;
import com.csc340sp23.OnlyPets.user.User;
import com.csc340sp23.OnlyPets.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    RatingService ratingService;

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

    @GetMapping("/like")
    @ResponseBody
    public int[] likePost(@RequestParam int postId, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);

        int[] response = new int[2];

        if(ratingService.hasLiked(user, post)) {
            response[0] = ratingService.getAllLikesByPost(post).size();
            response[1] = ratingService.getAllDislikesByPost(post).size();
            return response;
        }

        if(ratingService.hasDisliked(user, post)) {
            ratingService.deleteDislike(user, post);
        }

        postService.save(post);

        Likes like = new Likes(post, user);
        ratingService.saveLike(like);

        response[0] = ratingService.getAllLikesByPost(post).size();
        response[1] = ratingService.getAllDislikesByPost(post).size();

        return response;
    }

    @GetMapping("/dislike")
    @ResponseBody
    public int[] dislikePost(@RequestParam int postId, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);

        int[] response = new int[2];

        if(ratingService.hasDisliked(user, post)) {
            response[1] = ratingService.getAllLikesByPost(post).size();
            response[0] = ratingService.getAllDislikesByPost(post).size();
            return response;
        }

        if(ratingService.hasLiked(user, post)) {
            ratingService.deleteLike(user, post);
        }

        postService.save(post);

        Dislikes dislike = new Dislikes(post, user);
        ratingService.saveDislike(dislike);

        response[1] = ratingService.getAllLikesByPost(post).size();
        response[0] = ratingService.getAllDislikesByPost(post).size();

        return response;
    }

    @GetMapping("/delete")
    public void deletePost(@RequestParam int postId, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(postId);

        if(!user.getUsername().equals(post.getAuthor()) && !user.getRole().equals("MOD") && !user.getRole().equals("ADMIN"))  return;

        postService.deletePost(post);
    }
    @GetMapping("/report")
    public void reportPost(@RequestParam int postId, @RequestParam String username) {
        User user = userService.getUserByUsername(username);

        postService.reportPost(user.getId(), postId);
    }
}