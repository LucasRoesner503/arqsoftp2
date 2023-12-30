package com.isep.acme.repositories.redis;

import com.isep.acme.model.AggregatedRating;
import com.isep.acme.model.Product;
import com.isep.acme.repositories.AggregatedRatingRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RedisAggregatedRatingRepositoryImp extends RedisBaseRepository<AggregatedRating,Long> implements AggregatedRatingRepository {
    public RedisAggregatedRatingRepositoryImp() {
        super(AggregatedRating.class);
    }

    public RedisAggregatedRatingRepositoryImp(RedisTemplate<String, AggregatedRating> redisTemplate) {
        super(AggregatedRating.class, redisTemplate);
    }

    @Override
    public Optional<AggregatedRating> findByProductId(Product product) {
        Iterable<AggregatedRating> ar = findAll();

        for (AggregatedRating agr:ar) {
            if(Objects.equals(agr.getProduct().getProductID(), product.getProductID())){
                return Optional.of(agr);
            }
        }

        return Optional.empty();

    }
}
