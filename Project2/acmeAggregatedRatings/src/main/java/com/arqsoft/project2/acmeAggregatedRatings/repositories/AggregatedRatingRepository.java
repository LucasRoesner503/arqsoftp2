package com.arqsoft.project2.acmeAggregatedRatings.repositories;


import com.arqsoft.project2.acmeAggregatedRatings.model.AggregatedRating;
import com.arqsoft.project2.acmeAggregatedRatings.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AggregatedRatingRepository extends CrudRepository<AggregatedRating, Long> {

    @Query("SELECT a FROM AggregatedRating a WHERE a.product=:product")
    Optional<AggregatedRating> findByProductId(Product product);

}
