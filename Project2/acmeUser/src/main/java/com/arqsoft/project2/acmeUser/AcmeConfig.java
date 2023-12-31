package com.arqsoft.project2.acmeUser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.arqsoft.project2.acmeUser.repositories.*;
import com.arqsoft.project2.acmeUser.repositories.mongo.*;
import com.arqsoft.project2.acmeUser.repositories.neo4j.*;
import com.arqsoft.project2.acmeUser.repositories.redis.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AcmeConfig {

    @Value("${sku.generator}")
    private int skuAlgorithm;
    @Value("${review.algorithm}")
    private int reviewAlgorithm;
    @Value("${persistence.database}")
    private int database;


    @Primary
    @Bean
    public UserRepository getUserRepository(){
        return switch (database) {
            case 2 -> new Neo4JUserRepositoryImp();
            case 3 -> new RedisUserRepositoryImp();
            default -> new MongoUserRepositoryImp();
        };
    }
    
    @Bean
    public RedisTemplate getRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // Key serializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // Hash key serializer
        redisTemplate.setValueSerializer(getCustomJsonSerializer()); // Custom JSON serializer

        return redisTemplate;
    }
    @Bean
    public GenericJackson2JsonRedisSerializer getCustomJsonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configure the ObjectMapper to exclude unknown properties
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Exclude fields you don't want to serialize in your class
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Set class information to be stored as a property, similar to Jackson's default behavior
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        objectMapper.registerModule(new JavaTimeModule());

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }




}
