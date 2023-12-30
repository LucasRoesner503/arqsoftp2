package com.isep.acme.utilities;

import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.StringOperators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewAlgorithmC implements ReviewAlgorithm{


    private final ReviewAlgorithmA reviewAlgorithmA;
    private final ReviewAlgorithmB reviewAlgorithmB;

    @Override
    public List<Review> getReviewByAlgorithm(User user) {

        List<Review> a = reviewAlgorithmA.getReviewByAlgorithm(user);
        List<Review> b = reviewAlgorithmB.getReviewByAlgorithm(user);

        // Extract the users from List b
        List<User> searchUsers = b.stream()
                .map(Review::getUser)
                .collect(Collectors.toList());

        // Find reviews in List a made by users in searchUsers
        List<Review> reviewsFromSearchUsers = a.stream()
                .filter(review -> searchUsers.contains(review.getUser()))
                .collect(Collectors.toList());

        // Create a set of unique reviews from List B
        Set<Review> uniqueBReviews = new HashSet<>(b);

        // Filter out reviews in reviewsFromSearchUsers that are the same as those in uniqueBReviews
        reviewsFromSearchUsers = reviewsFromSearchUsers.stream()
                .filter(review -> !uniqueBReviews.contains(review))
                .collect(Collectors.toList());

        // Combine reviewsFromSearchUsers and uniqueBReviews into the final result list
        List<Review> combinedReviews = new ArrayList<>(reviewsFromSearchUsers);
        combinedReviews.addAll(uniqueBReviews);

        return combinedReviews;

    }
}
