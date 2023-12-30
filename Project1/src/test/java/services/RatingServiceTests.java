package services;
import com.isep.acme.model.Product;
import com.isep.acme.model.ProductDTO;
import com.isep.acme.model.Rating;
import com.isep.acme.repositories.ProductRepository;
import com.isep.acme.repositories.RatingRepository;
import com.isep.acme.services.ProductServiceImpl;
import com.isep.acme.services.RatingServiceImpl;
import com.isep.acme.utilities.SKUAlgorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import utilities.TestDataInitializer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RatingServiceTests.class)
public class RatingServiceTests {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Rating rating = tdi.createSampleRating();

    @Test
    void testFindByRate() {

        // Mock the behavior of the repository
        Mockito.when(ratingRepository.findByRate(TestDataInitializer.rate)).thenReturn(Optional.of(rating));

        // Call the service method
        Optional<Rating> result = ratingService.findByRate(TestDataInitializer.rate);

        // Verify that the result matches the mock Rating
        assertEquals(Optional.of(rating), result);
    }
}
