package com.csc340sp23.OnlyPets.settings;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
    @Table(name = "settings")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter

    public class Settings {

        @Id
        private int id;
        private String avatar;
        private Boolean banned;
        private int timedout;

        public Settings(int id) {
            this.id = id;
            this.avatar = "/assets/avatars/default.jpg";
            this.banned = false;
            this.timedout = 0;
        }
        
    }