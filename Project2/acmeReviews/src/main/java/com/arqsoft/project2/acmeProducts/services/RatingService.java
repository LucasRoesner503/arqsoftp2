package com.arqsoft.project2.acmeProducts.services;



import com.arqsoft.project2.acmeProducts.model.Rating;

import java.util.Optional;

public interface RatingService {

    Optional<Rating> findByRate(Double rate);
}
