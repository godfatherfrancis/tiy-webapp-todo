package com.tiy;

import javax.persistence.*;

/**
 * Created by Godfather on 5/17/2016.
 */
@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    boolean is_done;

    public ToDo() {
    }

    public ToDo(String text, boolean is_done) { // from view text
        this.text = text;
        this.is_done = is_done;
    }
}
