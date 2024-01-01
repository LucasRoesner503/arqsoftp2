package com.arqsoft.project2.acmeProducts.repositories.redis;




import com.arqsoft.project2.acmeProducts.model.Rating;
import com.arqsoft.project2.acmeProducts.repositories.RatingRepository;

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
