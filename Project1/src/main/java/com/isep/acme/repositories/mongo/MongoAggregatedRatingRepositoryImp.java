package com.isep.acme.repositories.mongo;

import com.isep.acme.model.AggregatedRating;
import com.isep.acme.model.Product;
import com.isep.acme.repositories.AggregatedRatingRepository;
import com.isep.acme.repositories.mongo.MongoBaseRepository;

import java.util.Optional;

public class MongoAggregatedRatingRepositoryImp extends MongoBaseRepository<AggregatedRating,Long> implements AggregatedRatingRepository {
    public MongoAggregatedRatingRepositoryImp() {
        super(AggregatedRating.class);
    }

    @Override
    public Optional<AggregatedRating> findByProductId(Product product) {
        Iterable<AggregatedRating> list = findAll();

        for(AggregatedRating ar : list){
            if(ar.getProduct().equals(product)){
                return Optional.of(ar);
            }
        }
        return Optional.empty();
    }
}
