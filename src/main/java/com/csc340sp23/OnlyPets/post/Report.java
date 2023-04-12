package com.csc340sp23.OnlyPets.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int reported_by;
    int post;
    public Report(int user, int post) {
        this.reported_by = user;
        this.post = post;
    }
}
