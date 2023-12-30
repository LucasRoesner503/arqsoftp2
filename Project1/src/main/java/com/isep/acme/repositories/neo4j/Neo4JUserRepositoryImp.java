package com.isep.acme.repositories.neo4j;

import com.isep.acme.model.User;
import com.isep.acme.property.EntityNotFoundException;
import com.isep.acme.repositories.UserRepository;

import java.util.Map;
import java.util.Optional;

public class Neo4JUserRepositoryImp extends Neo4JBaseRepository<User, Long> implements UserRepository {

    public Neo4JUserRepositoryImp() {
        super(User.class);
    }


    @Override
    public User getById(Long userId) {
        return neo4JRepo.findById(userId, User.class).orElse(null);

    }

    @Override
    public Optional<User> findByUsername(String username) {
        Iterable<User> u = findAll();
        for (User user:u) {
            if(user.getUsername().equals(username)){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
