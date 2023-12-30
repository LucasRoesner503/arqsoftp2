package com.isep.acme.repositories.redis;

import com.isep.acme.model.Product;
import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import com.isep.acme.model.Vote;
import com.isep.acme.repositories.ReviewRepository;
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
    public Optional<List<Review>> findByProductId(Product product) {
        List<Review> result = new ArrayList<>();
        Iterable<Review> allReviews = findAll();

        for (Review review : allReviews) {
            if (review.getProduct().equals(product)) {
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
