package com.arqsoft.project2.acmeUser.repositories.neo4j;

import com.arqsoft.project2.acmeUser.model.User;
import com.arqsoft.project2.acmeUser.repositories.UserRepository;

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
