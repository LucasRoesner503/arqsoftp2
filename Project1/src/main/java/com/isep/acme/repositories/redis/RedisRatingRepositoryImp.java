package com.isep.acme.repositories.redis;


import com.isep.acme.model.Rating;
import com.isep.acme.repositories.RatingRepository;

import java.util.Optional;

public class RedisRatingRepositoryImp extends RedisBaseRepository<Rating,Long> implements RatingRepository {



    public RedisRatingRepositoryImp(){
        super(Rating.class);

    }


    @Override
    public Optional<Rating> findByRate(Double rate) {
        Iterable<Rating> allRatings = findAll();

        for (Rating rating : allRatings) {
            if (rating.getRate().equals(rate)) {
                return Optional.of(rating);
            }
        }

        return Optional.empty();
    }
}
