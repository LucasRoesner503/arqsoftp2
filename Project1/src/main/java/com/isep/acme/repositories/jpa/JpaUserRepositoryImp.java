/*package com.isep.acme.repositories.jpa;

import com.isep.acme.repositories.UserRepository;
import org.springframework.cache.annotation.CacheConfig;

import org.springframework.jdbc.core.JdbcTemplate;

import com.isep.acme.model.User;

import java.util.Optional;


@CacheConfig(cacheNames = "users")
public class JpaUserRepositoryImp extends JpaBaseRepository<User, Long> implements UserRepository {


    public JpaUserRepositoryImp() {
        super(User.class, "User");
    }

    public JpaUserRepositoryImp(JdbcTemplate jdbcTemplate) {
        super(User.class, jdbcTemplate, "User");
    }

    @Override
    public User getById(Long userId) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}

 */