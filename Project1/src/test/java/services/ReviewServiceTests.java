package services;

import com.isep.acme.model.*;
import com.isep.acme.repositories.ReviewRepository;
import com.isep.acme.repositories.ProductRepository;
import com.isep.acme.repositories.UserRepository;
import com.isep.acme.services.RatingService;
import com.isep.acme.services.RestService;
import com.isep.acme.services.ReviewServiceImpl;
import com.isep.acme.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utilities.TestDataInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = ReviewServiceTests.class)
public class ReviewServiceTests {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestService restService;

    @Mock
    private UserService userService;

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

        Mockito.when(productRepository.findBySku(any(String.class))).thenReturn(productOptional);
        Mockito.when(userService.getUserId(any(Long.class))).thenReturn(userOptional);
        Mockito.when(ratingService.findByRate(any(Double.class))).thenReturn(ratingOptional);
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        ReviewDTO reviewDTO = reviewService.create(createReviewDTO, TestDataInitializer.sku);

        //todo
        assertNull(reviewDTO);
    }



}
