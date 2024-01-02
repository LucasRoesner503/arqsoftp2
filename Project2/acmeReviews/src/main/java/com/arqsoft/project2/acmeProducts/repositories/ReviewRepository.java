package com.arqsoft.project2.acmeProducts.repositories;


import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends CrudRepository<Review, Long> {

    //Optional<Review> findById(Long productId);

    @Query("SELECT r FROM Review r WHERE r.product.productID=:productId ORDER BY r.publishingDate DESC")
    Optional<List<Review>> findByProductId(Long productId);


    @Query("SELECT r FROM Review r WHERE r.approvalStatus='pending'")
    Optional<List<Review>> findPendingReviews();

    @Query("SELECT r FROM Review r WHERE r.approvalStatus='active'")
    Optional<List<Review>> findActiveReviews();

    @Query("SELECT r FROM Review r WHERE r.product=:product AND r.approvalStatus=:status ORDER BY r.publishingDate DESC")
    Optional<List<Review>> findByProductIdStatus(Product product, String status);

    @Query("SELECT r FROM Review r WHERE r.user=:user ORDER BY r.publishingDate DESC")
    Optional<List<Review>> findByUserId(User user);

    @Query("SELECT r FROM Review r WHERE r.user.userId=:userId AND r.approvalStatus='active' ORDER BY r.publishingDate DESC")
    Optional<List<Review>> findReviewsVotedByUserId(Long userId);

    //@Query("SELECT r FROM Review r")
   // Optional<List<Review>> findAllReviews();

}
