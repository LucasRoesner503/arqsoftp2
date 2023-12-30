/*
package com.isep.acme.repositories.jpa;


import com.isep.acme.model.Product;
import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import com.isep.acme.repositories.ReviewRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public class JpaReviewRepositoryImp extends JpaBaseRepository<Review,Long> implements ReviewRepository  {

    public JpaReviewRepositoryImp(){
        super(Review.class, "Review");
    }


    @Override
    public Optional<List<Review>> findByProductId(Long product_id) {
        return Optional.empty();
    }

    @Override
    @Query("SELECT r FROM Review r WHERE r.approvalStatus = 'pending'")
    public Optional<List<Review>> findPendingReviews() {
        return Optional.of(entityManager.createQuery("SELECT r FROM Review r WHERE r.approvalStatus = 'pending'", Review.class).getResultList());

    }

   @Override
    public Optional<List<Review>> findActiveReviews() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Review>> findByProductIdStatus(Product product, String status) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Review>> findByUserId(Long user_id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Review>> findAllReviews() {
        return Optional.empty();
    }
}
*/