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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    // Creates a new user with email username and pass from form
    public User(String username, String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        if (username.length() > 20 || email.length() > 255) return;
        this.email = email;
        this.username = username;
        this.password = bCryptPasswordEncoder.encode(password);
    }
}
