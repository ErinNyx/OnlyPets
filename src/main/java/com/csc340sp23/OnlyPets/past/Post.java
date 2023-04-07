package com.csc340sp23.OnlyPets.past;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int likes;
    int dislikes;
    int reports;
    int created_at;
    String author;
    String title;
    Boolean flagged;
    String picture;


    // Creates a new post with passed author and title
    public Post(String author, String title) {
        this.author = author;
        this.title = title;
    }
}
