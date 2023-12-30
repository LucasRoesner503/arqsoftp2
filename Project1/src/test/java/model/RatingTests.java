package model;

import com.isep.acme.model.Rating;
import org.junit.jupiter.api.Test;
import utilities.TestDataInitializer;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTests {

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Rating rating = tdi.createSampleRating();

    @Test
    void testConstructorWithAllAttributes() {
        // Arrange
        Double expectedRate = TestDataInitializer.rate;

        // Act
        Double actualRate = rating.getRate();

        // Assert
        assertEquals(expectedRate, actualRate);
    }

    @Test
    void testSetRate() {
        // Arrange
        Double newRate = 3.5;

        // Act
        rating.setRate(newRate);


        // Assert
        assertEquals(newRate, rating.getRate());
    }

    @Test
    void testGenerateRatingID() {
        // Act
        rating.generateRatingID();

        // Assert
        assertNotNull(rating.getId());
    }


}
