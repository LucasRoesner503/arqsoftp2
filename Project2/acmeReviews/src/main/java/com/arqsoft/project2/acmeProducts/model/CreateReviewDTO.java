package com.arqsoft.project2.acmeProducts.model;

public class CreateReviewDTO {

    private String reviewText;

    private Long userID;


    private Double rating;

    public CreateReviewDTO(){}

    public CreateReviewDTO(Long userID, String reviewText, Double rating){
        this.userID = userID;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public CreateReviewDTO(String reviewText) {
        this.reviewText = reviewText;
    }

    public CreateReviewDTO(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
