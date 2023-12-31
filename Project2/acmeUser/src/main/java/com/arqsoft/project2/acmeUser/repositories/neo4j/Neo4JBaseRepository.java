package com.arqsoft.project2.acmeUser.repositories.neo4j;


import com.arqsoft.project2.acmeUser.repositories.Idget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class Neo4JBaseRepository<T extends Idget, ID> implements CrudRepository<T, ID> {

    @Autowired
    protected Neo4jTemplate neo4JRepo;
    
    private final Class<T> tClass;

    public Neo4JBaseRepository(Class<T> tClass) {
        this.tClass = tClass;
    }


    @Override
    public <S extends T> S save(S entity) {
        return neo4JRepo.save(entity);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return neo4JRepo.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(neo4JRepo.findById(id, tClass).orElse(null));
    }

    @Override
    public boolean existsById(ID id) {
        return neo4JRepo.existsById(id, tClass);
    }

    @Override
    public Iterable<T> findAll() {
        return neo4JRepo.findAll(tClass);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return neo4JRepo.findAllById(ids, tClass);
    }

    @Override
    public long count() {
        return neo4JRepo.count(tClass);
    }

    @Override
    public void deleteById(ID id) {
        neo4JRepo.deleteById(id, tClass);
    }

    @Override
    public void delete(T entity) {
         neo4JRepo.deleteById(entity.getId(), tClass);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        neo4JRepo.deleteAllById(ids, tClass);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        neo4JRepo.deleteAll(entities.getClass());
    }

    @Override
    public void deleteAll() {
        neo4JRepo.deleteAll(tClass);

    }
}
