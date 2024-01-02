package com.arqsoft.project2.acmeAggregatedRatings.repositories.redis;


import com.arqsoft.project2.acmeAggregatedRatings.model.AggregatedRating;
import com.arqsoft.project2.acmeAggregatedRatings.model.Product;
import com.arqsoft.project2.acmeAggregatedRatings.repositories.AggregatedRatingRepository;
import org.springframework.data.redis.core.RedisTemplate;

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
