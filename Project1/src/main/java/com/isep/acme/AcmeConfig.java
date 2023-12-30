package com.isep.acme;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.isep.acme.model.AggregatedRating;
import com.isep.acme.model.Product;
import com.isep.acme.model.User;
import com.isep.acme.repositories.*;
import com.isep.acme.repositories.mongo.*;
import com.isep.acme.repositories.neo4j.*;
import com.isep.acme.repositories.redis.*;
import com.isep.acme.utilities.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    private ReviewRepository reviewRepository;

    @Bean
    public SKUAlgorithms getSkuGenerator() {
        return switch (skuAlgorithm) {
            case 2 -> new SKU_B();
            case 3 -> new SKU_C(new SKU_A(), new SKU_B());
            default -> new SKU_A();
        };
    }




    @Primary
    @Bean
    public ProductRepository getProductRepository() {
       return switch (database) {
            case 2 -> new Neo4JProductRepositoryImp();
            case 3 -> new RedisProductRepositoryImp();
            default -> new MongoProductRepositoryImp();
        };
    }


    @Primary
    @Bean
    public UserRepository getUserRepository(){
        return switch (database) {
            case 2 -> new Neo4JUserRepositoryImp();
            case 3 -> new RedisUserRepositoryImp();
            default -> new MongoUserRepositoryImp();
        };
    }

    @Primary
    @Bean
    public ImageRepository getImageRepository(){
        return switch (database) {
            case 2 -> new Neo4JImageRepositoryImp();
            case 3 -> new RedisImageRepositoryImp();
            default -> new MongoImageRepositoryImp();
        };
    }

    @Primary
    @Bean
    public ReviewRepository getReviewRepository(){
        return switch (database) {
            case 2 -> new Neo4JReviewRepositoryImp();
            case 3 -> new RedisReviewRepositoryImp();
            default -> new MongoReviewRepositoryImp();
        };
    }

    @Primary
    @Bean
    public RatingRepository getRatingRepository(){
        return switch (database) {
            case 2 -> new Neo4JRatingRepositoryImp();
            case 3 -> new RedisRatingRepositoryImp();
            default -> new MongoRatingRepositoryImp();
        };
    }

    @Primary
    @Bean
    public AggregatedRatingRepository getAggregatedRatingRepository(){
        return switch (database) {
            case 2 -> new Neo4JAggregatedRatingRepositoryImp();
            case 3 -> new RedisAggregatedRatingRepositoryImp();
            default -> new MongoAggregatedRatingRepositoryImp();
        };
    }


    @Bean
    public ReviewAlgorithm getReviewAlgorithm() {
        return switch (reviewAlgorithm) {
            case 2 -> new ReviewAlgorithmB(getReviewRepository());
            case 3 -> new ReviewAlgorithmC(new ReviewAlgorithmA(getReviewRepository()), new ReviewAlgorithmB(getReviewRepository()));
            default -> new ReviewAlgorithmA(getReviewRepository());
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
