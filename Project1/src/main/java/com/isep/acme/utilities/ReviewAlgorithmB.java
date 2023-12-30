package com.isep.acme.utilities;

import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import com.isep.acme.model.Vote;
import com.isep.acme.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ReviewAlgorithmB implements ReviewAlgorithm {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviewByAlgorithm(User user) {
        // get relevant reviews voted by the input user
        List<Review> userReviews = reviewRepository.findReviewsVotedByUserId(user.getUserId()).orElse(new ArrayList<>());
        Map<Long, Integer> userIdAndUpVotes = userReviews.stream().flatMap(review -> review.getUpVote().stream()).collect(Collectors.toMap(Vote::getUserID, vote -> 1, Integer::sum));
        Map<Long, Integer> userIdAndDownVotes = userReviews.stream().flatMap(review -> review.getDownVote().stream()).collect(Collectors.toMap(Vote::getUserID, vote -> 1, Integer::sum));
        userIdAndUpVotes.forEach((userId, integer) -> userIdAndDownVotes.merge(userId, integer, Integer::sum));
        userIdAndUpVotes.remove(user.getId());

        // get users with 50% more common votes
        Set<Long> userIdWith50PercentMoreCommonVotes = userIdAndUpVotes.entrySet().stream()
                .filter(currentUser -> {
                    long userId = currentUser.getKey();
                    List<Review> userReviewsCurrent = reviewRepository.findReviewsVotedByUserId(userId).orElse(new ArrayList<>());
                    long totalVotes = userReviewsCurrent.stream()
                            .flatMap(review -> Stream.concat(review.getUpVote().stream(), review.getDownVote().stream()))
                            .filter(vote -> vote.getUserID().equals(userId))
                            .count();
                    return (double) currentUser.getValue() / totalVotes >= 0.5;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        // fetch all reviews from the relevant users and remove the ones already voted by the input user
        List<Review> reviewsToRecommend = new ArrayList<>(userIdWith50PercentMoreCommonVotes.stream()
                .flatMap(userId -> reviewRepository.findByUserId(new User(userId)).orElse(new ArrayList<>()).stream())
                .collect(Collectors.toSet()));
        Set<Long> reviewsAlreadyVotedByUser = userReviews.stream().map(Review::getIdReview).collect(Collectors.toSet());
        reviewsToRecommend.removeIf(review -> reviewsAlreadyVotedByUser.contains(review.getIdReview()));
        return reviewsToRecommend;
    }
}
