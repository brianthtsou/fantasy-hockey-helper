package com.fantasyhockeyhelper.fantasy_hockey_helper_server;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;

    private String passwordHash;

    protected User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}