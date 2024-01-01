package com.arqsoft.project2.acmeProducts.repositories.mongo;


import com.arqsoft.project2.acmeProducts.model.Rating;
import com.arqsoft.project2.acmeProducts.repositories.RatingRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

public class MongoRatingRepositoryImp extends MongoBaseRepository<Rating,Long> implements RatingRepository {
    public MongoRatingRepositoryImp() {
        super(Rating.class);
    }

    public MongoRatingRepositoryImp(MongoTemplate mongoTemplate) {
        super(Rating.class, mongoTemplate);
    }

    @Override
    public Optional<Rating> findByRate(Double rate) {
        for (Rating r : this.findAll()) {
            if (r.getRate().equals(rate)) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }
}
