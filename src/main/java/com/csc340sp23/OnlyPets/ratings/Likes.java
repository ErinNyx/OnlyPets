package com.csc340sp23.OnlyPets.ratings;

import com.csc340sp23.OnlyPets.post.Post;
import com.csc340sp23.OnlyPets.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_likes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Likes {
    @Id
    private int user;
    private int post;
    public Likes(Post post, User user) {
        this.post = post.getId();
        this.user = user.getId();
    }
}
