package com.arqsoft.project2.acmeAuthentication.repositories.mongo;


import com.arqsoft.project2.acmeAuthentication.model.User;
import com.arqsoft.project2.acmeAuthentication.repositories.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoUserRepositoryImp extends MongoBaseRepository<User,Long> implements UserRepository {
    public MongoUserRepositoryImp() {
        super(User.class);
    }

    public MongoUserRepositoryImp(MongoTemplate mongoTemplate) {
        super(User.class, mongoTemplate);
    }

    @Override
    public User getById(Long userId) {
        return findById(userId).get();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        for (User u : all) {
            if (u.getUsername().equals(username)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
