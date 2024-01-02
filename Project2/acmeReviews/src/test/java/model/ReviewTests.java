package model;


import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.Vote;
import org.junit.jupiter.api.Test;
import utilities.TestDataInitializer;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ReviewTests {

    private final TestDataInitializer tdi = new TestDataInitializer();


    Review sampleReview = tdi.createSampleReview();

    @Test
    void testCreateSampleReview() {


        // Act & Assert
        assertNotNull(sampleReview);
        assertNotNull(sampleReview.getIdReview());
        assertNotNull(sampleReview.getApprovalStatus());
        assertNotNull(sampleReview.getReviewText());
        assertNotNull(sampleReview.getPublishingDate());
        assertNotNull(sampleReview.getFunFact());
        assertNotNull(sampleReview.getUpVote());
        assertNotNull(sampleReview.getDownVote());
        assertNotNull(sampleReview.getReport());
        assertNotNull(sampleReview.getProduct());
        assertNotNull(sampleReview.getRating());
        assertNotNull(sampleReview.getUser());
    }

    @Test
    void testSetApprovalStatus() {
        // Arrange


        // Act
        boolean result = sampleReview.setApprovalStatus(TestDataInitializer.approvedStatus);

        // Assert
        assertTrue(result);
        assertEquals("approved", sampleReview.getApprovalStatus());
    }

    @Test
    void testSetReviewText() {

        String newReviewText = "New review text";

        // Act
        sampleReview.setReviewText(newReviewText);

        // Assert
        assertEquals(newReviewText, sampleReview.getReviewText());
    }

    @Test
    void testAddUpVote() {

        Vote upVote = new Vote("upvote", 99L);

        // Act
        boolean added = sampleReview.addUpVote(upVote);

        // Assert
        assertTrue(added);
        assertTrue(sampleReview.getUpVote().contains(upVote));
    }

    @Test
    void testAddDownVote() {

        Vote downVote = new Vote("downvote", 98L);

        // Act
        boolean added = sampleReview.addDownVote(downVote);

        // Assert
        assertTrue(added);
        assertTrue(sampleReview.getDownVote().contains(downVote));
    }

}
