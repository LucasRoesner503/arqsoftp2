/*package com.isep.acme.repositories.jpa;

import com.isep.acme.model.Rating;
import com.isep.acme.repositories.RatingRepository;

import java.util.Optional;

public class JpaRatingRepositoryImp extends JpaBaseRepository<Rating,Long> implements RatingRepository {

    public JpaRatingRepositoryImp(){
        super(Rating.class, "Rating");
    }


    @Override
    public Optional<Rating> findByRate(Double rate) {
        return Optional.empty();
    }
}
*/