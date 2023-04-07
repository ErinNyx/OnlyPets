package com.csc340sp23.OnlyPets;

import com.csc340sp23.OnlyPets.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    // This is where you should also encode passwords, for simplicity's sake for now, I'm not using an encoder and
    // instead everything is currently stored as plain text. This is bad form, do not do this in production lol
    private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }


    // This filters user access

    // Because, for some reason, when the framework builds into the public folder it doesn't include static as a
    // directory I had to specify that /assets/** and /*.css were allowed as well.
    // If I had any JS I'd also have to include /*.js
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/register", "/password-reset", "/assets/**", "/*.css")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/register")
                        .permitAll()
                        .requestMatchers("/settings/**").hasAnyAuthority("USER")
                        .requestMatchers("/dashboard/**").hasAnyAuthority("MOD", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                ).exceptionHandling((x) -> x.accessDeniedPage("/403"))
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // Specifies what password encoder I'm using. You can see that for right now
    // I have my preferred encoder commented out. Also, be better than me. Don't use BCrypt, I think SHA256 is still one
    // of the best out there, I've just never used it before.
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}