package com.arqsoft.project2.acmeAggregatedRatings.repositories.mongo;



import com.arqsoft.project2.acmeAggregatedRatings.model.AggregatedRating;
import com.arqsoft.project2.acmeAggregatedRatings.model.Product;
import com.arqsoft.project2.acmeAggregatedRatings.repositories.AggregatedRatingRepository;

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
