package com.csc340sp23.OnlyPets.ratings;

import com.csc340sp23.OnlyPets.post.Post;
import com.csc340sp23.OnlyPets.post.PostRepository;
import com.csc340sp23.OnlyPets.user.User;
import com.csc340sp23.OnlyPets.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private LikesRepo likesRepo;
    @Autowired
    private DislikesRepo dislikesRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PostRepository postRepo;

    private Dislikes getDislike(int post, int user) {
        List<Dislikes> dislikes = getAllDislikes();
        return dislikes.stream().filter(dislike -> (dislike.getPost() == post && dislike.getUser() == user))
                .findFirst().orElse(null);
    }

    private Likes getLike(int post, int user) {
        List<Likes> likes = getAllLikes();
        return likes.stream().filter(like -> (like.getPost() == post && like.getUser() == user))
                .findFirst().orElse(null);
    }

    public List<Likes> getAllLikes() {
        return likesRepo.findAll();
    }

    public List<Dislikes> getAllDislikes() {
        return dislikesRepo.findAll();
    }

    public List<Likes> getAllLikesByPost(Post post) {
        List<Likes> likes = getAllLikes();
        return likes.stream().filter(like -> like.getPost() == post.getId()).toList();
    }

    public List<Dislikes> getAllDislikesByPost(Post post) {
        List<Dislikes> dislikes = getAllDislikes();
        return dislikes.stream().filter(dislike -> dislike.getPost() == post.getId()).toList();
    }

    public boolean hasLiked(User user, Post post) {
        List<Likes> likes = getAllLikes();
        return likes.stream().anyMatch(like -> (like.getUser() == user.getId() && like.getPost() == post.getId()));
    }
    public boolean hasDisliked(User user, Post post) {
        List<Dislikes> dislikes = getAllDislikes();
        return dislikes.stream().anyMatch(dislike -> (dislike.getUser() == user.getId() && dislike.getPost() == post.getId()));
    }

    @ResponseBody
    public String checkLike(User user, Post post) {
        if(hasLiked(user, post)) return "var(--link-hover)";
        else return "var(--link-color)";
    }

    @ResponseBody
    public String checkDislike(User user, Post post) {
        if(hasDisliked(user, post)) return "var(--link-hover)";
        else return "var(--link-color)";
    }

    public void saveLike(Likes like) {
        likesRepo.save(like);
    }

    public void saveDislike(Dislikes dislike) {
        dislikesRepo.save(dislike);
    }

    public void deleteLike(User user, Post post) {
        Likes like = getLike(post.getId(), user.getId());
        likesRepo.delete(like);
    }

    public void deleteDislike(User user, Post post) {
        Dislikes dislike = getDislike(post.getId(), user.getId());
        dislikesRepo.delete(dislike);
    }
}
