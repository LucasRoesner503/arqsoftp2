package com.arqsoft.project2.acmeProducts.utilities;



import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;

import java.util.List;

public interface ReviewAlgorithm {

    List<Review> getReviewByAlgorithm(User user);

}
