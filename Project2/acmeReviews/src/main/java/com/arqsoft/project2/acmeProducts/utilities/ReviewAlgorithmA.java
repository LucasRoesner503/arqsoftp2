package com.arqsoft.project2.acmeProducts.utilities;


import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewAlgorithmA implements ReviewAlgorithm{

    private final ReviewRepository repository;
    @Override
    public List<Review> getReviewByAlgorithm(User user) {

        Iterable<Review> reviews = repository.findAll();
        List<Review> sortedReviews = new ArrayList<Review>();
        reviews.iterator().forEachRemaining(sortedReviews::add);
        Collections.sort(sortedReviews, (o1, o2) -> {
            if (o1.getUpVote().size() > o2.getUpVote().size()) {
                return 1;
            } else if (o1.getUpVote().size() < o2.getUpVote().size()) {
                return -1;
            } else {
                return 0;
            }
        });

        List<Review> finalReviews = sortedReviews.stream().filter(review -> review.getUpVote().size() >= 4 && calc65PercentUpvote(review.getUpVote().size(), review.getDownVote().size())).collect(Collectors.toList());

        return finalReviews;
    }

    private boolean calc65PercentUpvote(float upVote, float downVote) {

        return upVote/(downVote+upVote) > 0.65;

    }

}
