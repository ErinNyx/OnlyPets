package com.csc340sp23.OnlyPets.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    @Autowired
    private ReportRepo reportRepo;

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

    public List<Report> getAllReports() {
        return reportRepo.findAll();
    }

    public Report getReport(int user, int post) {
        List<Report> reports = getAllReports();
        return reports.stream().filter(report -> (report.getReported_by() == user && report.getPost() == post))
                .findFirst().orElse(null);
    }

    public void reportPost(int user, int post) {
        if(getReport(user, post) != null) return;

        Report report = new Report(user, post);
        reportRepo.save(report);
    }
}