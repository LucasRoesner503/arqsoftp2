package services;

import com.arqsoft.project2.acmeProducts.model.Rating;
import com.arqsoft.project2.acmeProducts.repositories.RatingRepository;
import com.arqsoft.project2.acmeProducts.services.*;
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
