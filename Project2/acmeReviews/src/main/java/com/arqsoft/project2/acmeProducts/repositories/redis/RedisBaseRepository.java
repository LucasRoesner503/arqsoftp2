package com.arqsoft.project2.acmeProducts.repositories.redis;

import com.arqsoft.project2.acmeProducts.repositories.Idget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public class RedisBaseRepository<T extends Idget<ID>,ID> implements CrudRepository<T,ID>{


    @Qualifier("getRedisTemplate")
    @Autowired
    private RedisTemplate<String,T> redisTemplate;

    private final Class<T> domainClass;

    public RedisBaseRepository(Class<T> domainClass) {
        this.domainClass = domainClass;
    }
    public RedisBaseRepository(Class<T> domainClass, RedisTemplate<String,T> redisTemplate) {
        this.domainClass = domainClass;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public <S extends T> S save(S entity) {
        String redisKey = domainClass.getSimpleName() + ": " + entity.getId();
        redisTemplate.opsForValue().set(redisKey,entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        for(S entity : entities){
            savedEntities.add(save(entity));
        }
        return savedEntities;
    }

    @Override
    public Optional<T> findById(ID id) {
        String redisKey = domainClass.getSimpleName() + ": " + id;
        T entity = (T) redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<T> findAll() {
       Set<T> entityCollection = new HashSet<>();
       Set<String> keys = redisTemplate.keys(domainClass.getSimpleName() + ":*");
       for(String key : keys){
           T entity = redisTemplate.opsForValue().get(key);
           if(entity != null){
               entityCollection.add(entity);
           }
       }
       return entityCollection;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> entities = new ArrayList<>();
        for(ID id : ids){
            findById(id).ifPresent(entities::add);
        }
        return entities;
    }

    @Override
    public long count() {
        return Objects.requireNonNull(redisTemplate.keys("*")).size();
    }

    @Override
    public void deleteById(ID id) {
        String redisKey = domainClass.getSimpleName() + ": " + id;
        redisTemplate.delete(redisKey);
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());

    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity: entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        for(T entity : findAll()){
            delete(entity);
        }
    }
}
