package controller;


import com.arqsoft.project2.acmeProducts.controllers.ReviewController;
import com.arqsoft.project2.acmeProducts.model.*;
import com.arqsoft.project2.acmeProducts.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utilities.TestDataInitializer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = ReviewController.class)
@AutoConfigureMockMvc
@EnableWebMvc
@WithMockUser
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService rService;

    @InjectMocks
    private ReviewController reviewController;

    private final TestDataInitializer tdi = new TestDataInitializer();
    private final Review review = tdi.createSampleReview();


    @Test
    public void testFindById() throws Exception {

        List<ReviewDTO> reviewList = new ArrayList<>();
        reviewList.add(new ReviewDTO(review));
        when(rService.getReviewsOfProduct(review.getProduct().sku, TestDataInitializer.approvedStatus)).thenReturn(reviewList);

        // Act & Assert
        mockMvc.perform(get("/reviews/{status}/products/{sku}", review.getProduct().sku, TestDataInitializer.approvedStatus))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$", hasSize(reviewList.size())));
    }

    @Test
    public void testReviewByUser() throws Exception {
        List<ReviewDTO> reviewList = new ArrayList<>();
        reviewList.add(new ReviewDTO(review));

        when(rService.findReviewsByUser(review.getUser().getUserId())).thenReturn(reviewList);

        mockMvc.perform(get("/reviews/{userID}", review.getUser().getUserId()))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.size()").value(reviewList.size()));
    }

    @Test
    void testCreateReview() {
        // Create a sample CreateReviewDTO
        CreateReviewDTO createReviewDTO = new CreateReviewDTO(review.getUser().getUserId(), review.getReviewText(), review.getRating().getRate());


        // Mock the necessary service methods
        when(rService.create(any(CreateReviewDTO.class), any(String.class))).thenAnswer(invocation -> {
            // Extract arguments
            CreateReviewDTO dto = invocation.getArgument(0);
            String sku = invocation.getArgument(1);


           return new ReviewDTO(review);
        });

        // Test the controller method
        ResponseEntity<ReviewDTO> response = reviewController.createReview(TestDataInitializer.sku, createReviewDTO);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // You can further assert the response body if needed
        ReviewDTO reviewDTO = response.getBody();
        assertEquals(TestDataInitializer.originalUserId, review.getUser().getUserId());
        assert reviewDTO != null;
        assertEquals(TestDataInitializer.reviewText, reviewDTO.getReviewText());
        // Add more assertions as needed
    }

    @Test
    public void testAddVoteUpVote() {
        // Create a sample VoteReviewDTO for upVote
        VoteReviewDTO voteReviewDTO = new VoteReviewDTO();
        voteReviewDTO.setVote("upvote");
        voteReviewDTO.setUserID(TestDataInitializer.userId);


        Vote vote = new Vote("upVote", 1L);

        when(rService.addVoteToReview(review.getIdReview(), voteReviewDTO)).thenReturn(true);

        ResponseEntity<Boolean> response = reviewController.addVote(review.getIdReview(), voteReviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    public void testDeleteReviewA() {
        // Define a sample review ID for testing
        Long reviewId = TestDataInitializer.idReview;

        // Mock the service method to return true when DeleteReview is called
        Mockito.when(rService.DeleteReview(reviewId)).thenReturn(true);

        // Execute the controller method
        ResponseEntity<Boolean> response = reviewController.deleteReview(reviewId);

        // Assert the response
        assertEquals(ResponseEntity.ok(true), response);
    }

    @Test
    public void testGetPendingReview() throws Exception {

        List<ReviewDTO> reviewDTOlist = new ArrayList<>();
        ReviewDTO reviewDTO = new ReviewDTO(review);
        reviewDTO.setApprovalStatus(TestDataInitializer.pendingStatus);
        reviewDTOlist.add(reviewDTO);
        when(rService.findPendingReview()).thenReturn(reviewDTOlist);

        // Create an ObjectMapper with JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        // Act & Assert
        mockMvc.perform(get("/reviews/pending")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewDTOlist))
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.size()").value(reviewDTOlist.size()));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
