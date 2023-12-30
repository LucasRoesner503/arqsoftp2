package com.isep.acme.repositories.mongo;

import com.isep.acme.model.Product;
import com.isep.acme.model.Rating;
import com.isep.acme.repositories.RatingRepository;
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
