package com.csc340sp23.OnlyPets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // returns a list of all users
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // returns a user by id
    public User getUserById(int userId) {
        return repo.getReferenceById(userId);
    }

    // For these next two, the .findOne notation wouldn't just take a string and when I looked online they were like
    // "EUGH Why would you need to find by a string anyway just use the ID EUGH My name's SmartyMcSmartyPants and I
    // know everything and btw your mom ha gotcha" so anyway I'm just iterating over a list here with a super smart
    // method I'm proud of called not being stupid and making a for loop

    // Filters list of users and returns user object where username == param
    // or else null
    public User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    // Filters list of users and returns user object where email == param
    // or else null
    public User getUserByEmail(String email) {
        List<User> users = getAllUsers();
        return users.stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    // saves user

    public void save(User user) {
        repo.save(user);
    }
}