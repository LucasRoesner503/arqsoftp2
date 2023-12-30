/*package com.isep.acme.repositories.jpa;

import com.isep.acme.repositories.Idget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.Optional;


public class JpaBaseRepository <T extends Idget<ID>, ID> implements CrudRepository<T, ID> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Class<T> domainClass;

    private final String tableName;


    public JpaBaseRepository(Class<T> domainClass, String tableName){
        this.domainClass = domainClass;
        this.tableName = tableName;
    }
    public JpaBaseRepository(Class<T> domainClass, JdbcTemplate jdbcTemplate, String tableName) {
        this.domainClass = domainClass;
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }


    @Override
    public <S extends T> S save(S entity) {
       return null;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
*/