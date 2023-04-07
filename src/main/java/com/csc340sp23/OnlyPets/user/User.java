package com.csc340sp23.OnlyPets.user;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String email;
    private String username;
    private String password;
    private String role;
    private String avatar;

    // Creates a new user with email username and pass from form
    public User(String username, String email, String password) {
        if (username.length() > 20 || email.length() > 255) return;
        this.email = email;
        this.username = username;
        // THE FOLLOWING IS BAD PRACTICE. DO NOT DO IN MOST LANGUAGES BUT HERE IS FINE
        this.password = password;
    }
}
