package com.ssafy.trippals.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor @Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    private String name;
    private String password;
    private String email;
    private String profileImage;
    private LocalDate registerDate;

    public User(int seq) {
        this.seq = seq;
    }
}
