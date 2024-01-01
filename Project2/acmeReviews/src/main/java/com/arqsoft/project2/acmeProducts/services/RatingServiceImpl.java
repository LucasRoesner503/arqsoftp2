package com.arqsoft.project2.acmeProducts.services;


import com.arqsoft.project2.acmeProducts.model.Rating;
import com.arqsoft.project2.acmeProducts.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    RatingRepository repository;

    public Optional<Rating> findByRate(Double rate){
        return repository.findByRate(rate);
    }

}
