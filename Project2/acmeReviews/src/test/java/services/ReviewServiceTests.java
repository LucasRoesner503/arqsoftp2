package services;


import com.arqsoft.project2.acmeProducts.model.*;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;
import com.arqsoft.project2.acmeProducts.services.RatingService;
import com.arqsoft.project2.acmeProducts.services.RestService;
import com.arqsoft.project2.acmeProducts.services.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import utilities.TestDataInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = ReviewServiceTests.class)
public class ReviewServiceTests {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestService restService;

    @Mock
    private RatingService ratingService;

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Review review = tdi.createSampleReview();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        Mockito.when(reviewRepository.findAll()).thenReturn(reviews);

        Iterable<Review> result = reviewService.getAll();

        assertNotNull(result);
        List<Review> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(2, resultList.size());
    }

    @Test
    public void testCreateReview() {
        CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setUserID(1L);
        createReviewDTO.setRating(4.5);
        createReviewDTO.setReviewText("Test review");

        Product product = review.getProduct();
        Optional<Product> productOptional = Optional.of(product);

        User user = review.getUser();
        Optional<User> userOptional = Optional.of(user);

        Rating rating = review.getRating();
        Optional<Rating> ratingOptional = Optional.of(rating);


        Mockito.when(ratingService.findByRate(any(Double.class))).thenReturn(ratingOptional);
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        ReviewDTO reviewDTO = reviewService.create(createReviewDTO, TestDataInitializer.sku);

        //todo
        assertNull(reviewDTO);
    }



}
