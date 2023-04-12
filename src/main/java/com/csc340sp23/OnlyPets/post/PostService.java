package com.csc340sp23.OnlyPets.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    public Post getPostById(int postId) {
        return repo.getReferenceById(postId);
    }

    public Post getPostByAuthor(String username) {
        List<Post> posts = getAllPosts();
        return posts.stream().filter(post -> post.getAuthor() == username)
                .findFirst().orElse(null);
    }

    public void save(Post post) {
        repo.save(post);
    }

    public void deletePost(Post post) {
        repo.delete(post);
    }
}