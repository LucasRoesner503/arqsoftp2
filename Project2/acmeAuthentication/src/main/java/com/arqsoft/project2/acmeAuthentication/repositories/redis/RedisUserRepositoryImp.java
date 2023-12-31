package com.arqsoft.project2.acmeAuthentication.repositories.redis;




import com.arqsoft.project2.acmeAuthentication.model.User;
import com.arqsoft.project2.acmeAuthentication.property.EntityNotFoundException;
import com.arqsoft.project2.acmeAuthentication.repositories.UserRepository;

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
