package com.arqsoft.project2.acmeProducts.repositories.mongo;



import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MongoReviewRepositoryImp extends MongoBaseRepository<Review,Long> implements ReviewRepository {
    public MongoReviewRepositoryImp() {
        super(Review.class);
    }

    public MongoReviewRepositoryImp(MongoTemplate mongoTemplate) {
        super(Review.class, mongoTemplate);
    }

    @Override
    public Optional<List<Review>> findByProductId(Long productId) {
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all.stream().filter(r -> r.getProduct().getProductID() == productId).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Review>> findPendingReviews() {

        Query query = new Query();
        query.addCriteria(Criteria.where("approvalStatus").is("pending"));
        List<Review> result = StreamSupport.stream(this.find(query).spliterator(), false)
                .collect(Collectors.toList());
        return Optional.of(result);
        //List<Review> all = new ArrayList<>();
        //this.findAll().forEach(all::add);
        //return Optional.of(all.stream().filter(r -> r.getApprovalStatus().equals("Pending")).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Review>> findActiveReviews() {
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all.stream().filter(r -> r.getApprovalStatus().equals("Active")).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Review>> findByProductIdStatus(Product product, String status) {
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all.stream().filter(r -> r.getProduct().equals(product) && r.getApprovalStatus().equals(status)).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Review>> findByUserId(User user) {
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all.stream().filter(r -> r.getUser().getUserId().equals(user.getUserId())).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Review>> findReviewsVotedByUserId(Long userId){
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all.stream().filter(r -> r.getUpVote().stream().anyMatch(v -> v.getUserID().equals(userId)) || r.getDownVote().stream().anyMatch(v -> v.getUserID().equals(userId))).collect(Collectors.toList()));
    }

/*    @Override
    public Optional<List<Review>> findAllReviews() {
        List<Review> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return Optional.of(all);
    }*/
}
