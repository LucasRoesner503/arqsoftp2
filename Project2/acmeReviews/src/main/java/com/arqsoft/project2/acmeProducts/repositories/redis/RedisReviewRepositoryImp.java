package com.arqsoft.project2.acmeProducts.repositories.redis;


import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;
import com.arqsoft.project2.acmeProducts.model.Vote;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RedisReviewRepositoryImp extends RedisBaseRepository<Review,Long> implements ReviewRepository {

    public RedisReviewRepositoryImp() {
        super(Review.class);
    }

    public RedisReviewRepositoryImp(RedisTemplate<String, Review> redisTemplate) {
        super(Review.class, redisTemplate);
    }

    @Override
    public Optional<List<Review>> findByProductId(Long productId) {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getProduct().getProductID().equals(productId)) {
                result.add(review);
            }
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Review>> findPendingReviews() {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getApprovalStatus().equals("pending")) {
                result.add(review);
            }
        }
        return Optional.of(result);
    }

   @Override
    public Optional<List<Review>> findActiveReviews() {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getApprovalStatus().equals("active")) {
                result.add(review);
            }
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Review>> findByProductIdStatus(Product product, String status) {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getProduct().equals(product) && review.getApprovalStatus().equals(status)) {
                result.add(review);
            }
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Review>> findByUserId(User user) {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getUser().getUserId().equals(user.getUserId())) {
                result.add(review);
            }
        }
        return Optional.of(result);
    }



    @Override
    public Optional<List<Review>> findReviewsVotedByUserId(Long userId) {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();
        List<Vote> uplist;
        List<Vote> downlist;

        for (Review review : allReviews) {
            uplist = review.getUpVote();
            downlist = review.getDownVote();
            for (Vote vote : uplist) {
                if (vote.getUserID().equals(userId)) {
                    result.add(review);
                }
            }
            for (Vote vote : downlist) {
                if (vote.getUserID().equals(userId)) {
                    result.add(review);
                }
            }
        }
        return Optional.of(result);
    }

  /*  @Override
    public Optional<List<Review>> findAllReviews() {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
                result.add(review);

        }
        return Optional.of(result);
    }*/
}
