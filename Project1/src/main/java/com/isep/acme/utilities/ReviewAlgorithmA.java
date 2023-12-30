package com.isep.acme.utilities;

import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import com.isep.acme.services.ReviewServiceImpl;
import com.isep.acme.model.Vote;
import com.isep.acme.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
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
