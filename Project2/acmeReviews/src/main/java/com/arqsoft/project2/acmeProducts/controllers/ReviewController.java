package com.arqsoft.project2.acmeProducts.controllers;


import com.arqsoft.project2.acmeProducts.model.CreateReviewDTO;
import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.ReviewDTO;
import com.arqsoft.project2.acmeProducts.model.VoteReviewDTO;
import com.arqsoft.project2.acmeProducts.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Review", description = "Endpoints for managing Review")
@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService rService;

    public ReviewController(ReviewService rService){
        this.rService = rService;
    }

    @Operation(summary = "finds a product through its sku and shows its review by status")
    @GetMapping("/{status}/products/{sku}")
    public ResponseEntity<List<ReviewDTO>> findById(@PathVariable(value = "sku") final String sku, @PathVariable(value = "status") final String status) {

        final var review = rService.getReviewsOfProduct(sku, status);

        return ResponseEntity.ok().body( review );
    }

    @Operation(summary = "gets review by user")
    @GetMapping("/{userID}")
    public ResponseEntity<List<ReviewDTO>> findReviewByUser(@PathVariable(value = "userID") final Long userID) {

        final var review = rService.findReviewsByUser(userID);

        return ResponseEntity.ok().body(review);
    }

    @Operation(summary = "creates review")
    @PostMapping("/products/{sku}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable(value = "sku") final String sku, @RequestBody CreateReviewDTO createReviewDTO) {

        final var review = rService.create(createReviewDTO, sku);

        if(review == null){
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<ReviewDTO>(review, HttpStatus.CREATED);
    }

    @Operation(summary = "get weighted average")
    @GetMapping("/average/{productId}")
    public ResponseEntity<Double> createReview(@PathVariable(value = "productId") final Long productId) {

        final Double value = rService.getWeightedAverage(productId);

        if(value == null){
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<Double>(value, HttpStatus.CREATED);
    }

    @Operation(summary = "add vote")
    @PutMapping("/{reviewID}/vote")
    public ResponseEntity<Boolean> addVote(@PathVariable(value = "reviewID") final Long reviewID, @RequestBody VoteReviewDTO voteReviewDTO){

        boolean added = this.rService.addVoteToReview(reviewID, voteReviewDTO);

        if(!added){
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<Boolean>(added, HttpStatus.CREATED);
    }

    @Operation(summary = "deletes review")
    @DeleteMapping("/{reviewID}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable(value = "reviewID") final Long reviewID) {

        Boolean rev = rService.DeleteReview(reviewID);

        if (rev == null) return ResponseEntity.notFound().build();

        if (rev == false) return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.ok().body(rev);
    }

    @Operation(summary = "gets pedding reviews")
    @GetMapping("/pending")
    public ResponseEntity<List<ReviewDTO>> getPendingReview(){

        List<ReviewDTO> r = rService.findPendingReview();

        return ResponseEntity.ok().body(r);
    }

    @Operation(summary = "Accept or reject review")
    @PutMapping("/acceptreject/{reviewID}")
    public ResponseEntity<ReviewDTO> putAcceptRejectReview(@PathVariable(value = "reviewID") final Long reviewID, @RequestBody String approved){

        try {
            ReviewDTO rev = rService.moderateReview(reviewID, approved);

            return ResponseEntity.ok().body(rev);
        }
        catch( IllegalArgumentException e ) {
            return ResponseEntity.badRequest().build();
        }
        catch( ResourceNotFoundException e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Accept or reject review")
    @PatchMapping("/recommend/{reviewID}/user/{userID}")
    public ResponseEntity<ReviewDTO> recommendReview(@PathVariable(value = "reviewID") final Long reviewID, @PathVariable(value = "userID") final Long userID){

        try {
            ReviewDTO rev = rService.recommendReview(reviewID, userID);

            return ResponseEntity.ok().body(rev);
        }
        catch( IllegalArgumentException e ) {
            return ResponseEntity.badRequest().build();
        }
        catch( ResourceNotFoundException e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "gets review algorothm")
    @GetMapping("/algorithm/{userID}")
    public ResponseEntity<List<ReviewDTO>> getReviewAlgorithm(@PathVariable(value = "userID") final Long userID){

        List<ReviewDTO> r = rService.getReviewAlgorithm(userID);

        return ResponseEntity.ok().body(r);
    }
}
