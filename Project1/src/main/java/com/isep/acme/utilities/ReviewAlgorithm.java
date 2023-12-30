package com.isep.acme.utilities;

import com.isep.acme.model.Review;
import com.isep.acme.model.User;

import java.util.List;

public interface ReviewAlgorithm {

    List<Review> getReviewByAlgorithm(User user);

}
