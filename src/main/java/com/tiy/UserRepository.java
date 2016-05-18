package com.tiy;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Godfather on 5/18/2016.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}
