package com.tiy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Godfather on 5/17/2016.
 */
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

    @Query("SELECT g FROM ToDo g WHERE g.text LIKE ?1%")
    List<ToDo> findByNameStartsWith(String name);
}
