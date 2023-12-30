package com.isep.acme.repositories.mongo;

import com.isep.acme.repositories.Idget;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;


public class MongoBaseRepository<T extends Idget<ID>, ID> implements CrudRepository<T, ID> {

    //@Autowired
    //protected MongoDatabase mongoDB;


    @Autowired
    private MongoTemplate mongoTemplate;
    private final Class<T> domainClass;

    public MongoBaseRepository (Class<T> domainClass){
        this.domainClass = domainClass;
    }

    public MongoBaseRepository (Class<T> domainClass, MongoTemplate mongoTemplate){
        this.domainClass = domainClass;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public long count() {
        return mongoTemplate.findAll(domainClass).size();
    }

    @Override
    public void delete(T entity) {
        mongoTemplate.remove(entity);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(domainClass);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity: entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id: ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(ID id) {
        mongoTemplate.findById(id, domainClass);
    }

    @Override
    public boolean existsById(ID id) {
        return mongoTemplate.findById(id, domainClass) != null;
    }

    @Override
    public Iterable<T> findAll() {
        return mongoTemplate.findAll(domainClass);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> entities = null;
        for (ID id: ids) {
            if(existsById(id)) {
                T entity = mongoTemplate.findById(id, domainClass);
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(mongoTemplate.findById(id, domainClass));
    }

    @Override
    public <S extends T> S save(S entity) {
        mongoTemplate.save(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity: entities) {
            save(entity);

        }
        return entities;
    }

    public Iterable<T> find(Query query) {
        return mongoTemplate.find(query, this.domainClass);
    }
}
