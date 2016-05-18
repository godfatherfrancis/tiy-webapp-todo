package com.tiy;

import javax.persistence.*;

/**
 * Created by Godfather on 5/18/2016.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true) // unique = true to the username just as an additional constraint
    String name;

    @Column(nullable = false)
    String password;

    public String getPassword() {
        return password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
    }
}