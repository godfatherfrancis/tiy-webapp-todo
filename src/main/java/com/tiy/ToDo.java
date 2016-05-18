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

    @ManyToOne //  add a foreign key in the database called user_id for the purposes of doing joins
    User user;

    public ToDo() {
    }

    public ToDo(String text, boolean is_done, User user) { // from view text
        this.text = text;
        this.is_done = is_done;
        this.user = user;
    }


}
