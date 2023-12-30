package com.isep.acme.repositories.redis;

import com.isep.acme.model.Product;
import com.isep.acme.model.User;
import com.isep.acme.property.EntityNotFoundException;
import com.isep.acme.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RedisUserRepositoryImp extends RedisBaseRepository<User,Long> implements UserRepository {


    public RedisUserRepositoryImp(){
        super(User.class);
    }


    @Override
    public User getById(Long userId) {
        List<User> result = new ArrayList<>();
        Iterable<User> allUsers = findAll();
        for (User user : allUsers) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }

        throw new EntityNotFoundException("The user with ID " + userId + " was not found in the system");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Iterable<User> allUsers = findAll();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
