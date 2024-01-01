package com.arqsoft.project2.acmeProducts.services;


import com.arqsoft.project2.acmeProducts.controllers.ResourceNotFoundException;
import com.arqsoft.project2.acmeProducts.model.*;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;
import com.arqsoft.project2.acmeProducts.utilities.ReviewAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository repository;
    @Autowired
    RatingService ratingService;

    @Autowired
    RestService restService;

    @Autowired
    ReviewAlgorithm reviewAlgorithm;

    @Value("${gateway.url}")
    private String url;


    @Override
    public Iterable<Review> getAll() {
        return repository.findAll();
    }

    @Override
    public ReviewDTO create(final CreateReviewDTO createReviewDTO, String sku) {

        final Optional<Product> product = this.getProduct(sku);

        if(product.isEmpty()) return null;

        final var user = this.getUser(createReviewDTO.getUserID());

        if(user.isEmpty()) return null;

        Rating rating = null;
        Optional<Rating> r = ratingService.findByRate(createReviewDTO.getRating());
        if(r.isPresent()) {
            rating = r.get();
        }

        LocalDate date = LocalDate.now();

        String funfact = restService.getFunFact(date);

        if (funfact == null) return null;

        Review review = new Review(createReviewDTO.getReviewText(), date, product.get(), funfact, rating, user.get());

        review = repository.save(review);

        if (review == null) return null;

        return ReviewMapper.toDto(review);
    }

    private Optional<User> getUser(Long userId) {
        try{

            URL endpointUrl = new URL(url + "/admin/user/" + userId);
            HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();

            User user = objectMapper.readValue(response.toString(), User.class);

            return Optional.of(user);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

    private Optional<Product> getProduct(String sku) {
        try{

            URL endpointUrl = new URL(url + "/products/" + sku);
            HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();

            Product product = objectMapper.readValue(response.toString(), Product.class);

            return Optional.of(product);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

    @Override
    public List<ReviewDTO> getReviewsOfProduct(String sku, String status) {

        Optional<Product> product = this.getProduct(sku);
        if( product.isEmpty() ) return null;

        Optional<List<Review>> r = repository.findByProductIdStatus(product.get(), status);

        if (r.isEmpty()) return null;

        return ReviewMapper.toDtoList(r.get());
    }

    @Override
    public boolean addVoteToReview(Long reviewID, VoteReviewDTO voteReviewDTO) {

        Optional<Review> review = this.repository.findById(reviewID);

        if (review.isEmpty()) return false;

        Vote vote = new Vote(voteReviewDTO.getVote(), voteReviewDTO.getUserID());
        if (voteReviewDTO.getVote().equalsIgnoreCase("upVote")) {
            boolean added = review.get().addUpVote(vote);
            if (added) {
                Review reviewUpdated = this.repository.save(review.get());
                return reviewUpdated != null;
            }
        } else if (voteReviewDTO.getVote().equalsIgnoreCase("downVote")) {
            boolean added = review.get().addDownVote(vote);
            if (added) {
                Review reviewUpdated = this.repository.save(review.get());
                return reviewUpdated != null;
            }
        }
        return false;
    }

    @Override
    public Double getWeightedAverage(Product product){

        Optional<List<Review>> r = repository.findByProductId(product);

        if(r.isEmpty() || r.get().isEmpty()){
            return 0.0;
        }


        double sum = 0;

        for (Review rev: r.get()) {
            Rating rate = rev.getRating();

            if (rate != null){
                sum += rate.getRate();
            }
        }

        return sum/r.get().size();
    }

    @Override
    public Boolean DeleteReview(Long reviewId)  {

        Optional<Review> rev = repository.findById(reviewId);

        if (rev.isEmpty()){
            return null;
        }
        Review r = rev.get();

        if (r.getUpVote().isEmpty() && r.getDownVote().isEmpty()) {
            repository.delete(r);
            return true;
        }
        return false;
    }

    @Override
    public List<ReviewDTO> findPendingReview(){

        Optional<List<Review>> r = repository.findPendingReviews();

        if(r.isEmpty()){
            return null;
        }

        return ReviewMapper.toDtoList(r.get());
    }

    @Override
    public ReviewDTO moderateReview(Long reviewID, String approved) throws ResourceNotFoundException, IllegalArgumentException {

        Optional<Review> r = repository.findById(reviewID);

        if(r.isEmpty()){
            throw new ResourceNotFoundException("Review not found");
        }

        Boolean ap = r.get().setApprovalStatus(approved);

        if(!ap) {
            throw new IllegalArgumentException("Invalid status value");
        }

        Review review = repository.save(r.get());

        return ReviewMapper.toDto(review);
    }


    @Override
    public List<ReviewDTO> findReviewsByUser(Long userID) {

        final Optional<User> user = this.getUser(userID);

        if(user.isEmpty()) return null;

        Optional<List<Review>> r = repository.findByUserId(user.get());

        if (r.isEmpty()) return null;

        return ReviewMapper.toDtoList(r.get());
    }

    @Override
    public List<ReviewDTO> getReviewAlgorithm(Long userID) {

        final Optional<User> user = this.getUser(userID);
            Optional<List<Review>> sortedList = Optional.ofNullable(reviewAlgorithm.getReviewByAlgorithm(user.orElse(null)));
            return ReviewMapper.toDtoList(sortedList.orElse(null));
    }
}