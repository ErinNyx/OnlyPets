package com.csc340sp23.OnlyPets.ratings;

import com.csc340sp23.OnlyPets.post.Post;
import com.csc340sp23.OnlyPets.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RatingController {
    @Autowired
    RatingService ratingService;
}
