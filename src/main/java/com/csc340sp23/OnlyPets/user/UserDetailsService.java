package com.csc340sp23.OnlyPets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UserService userService;

    // This is called in the websecurityconfig file in configureGlobal for every authentication.
    // I just have a custom UserService which isn't recommended apparently by like everyone
    // WHO CARES
    // So this was my solution
    // Checks if Username exists, if not it throws an exception which I still need to write the logic for on the front
    // end to alert the user that the username was invalid. I also still need to write the logic for users who use the
    // wrong password.

    // The logic here after that is pretty self-explanatory, I just had to hardcode things like isAccountNonExpired()
    // to return true. Later, when timeout is fully implemented, Duncan will need to go in here and write the logic for
    // preventing timed out users from logging in.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if(user == null) throw new UsernameNotFoundException("Username not found: " + username);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
