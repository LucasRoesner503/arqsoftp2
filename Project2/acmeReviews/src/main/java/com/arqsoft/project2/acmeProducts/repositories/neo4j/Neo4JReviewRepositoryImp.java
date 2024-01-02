package com.arqsoft.project2.acmeProducts.repositories.neo4j;



import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.Review;
import com.arqsoft.project2.acmeProducts.model.User;
import com.arqsoft.project2.acmeProducts.repositories.ReviewRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Neo4JReviewRepositoryImp extends Neo4JBaseRepository<Review, Long> implements ReviewRepository {

    public Neo4JReviewRepositoryImp() {
        super(Review.class);
    }


    @Override
    public Optional<List<Review>> findByProductId(Long product) {
        String cypherQuery = "MATCH (r:Review)-[:REVIEWS] -> (p:Product) WHERE p.productId = $productId RETURN r";
        Map<String, Object> params = Map.of("productId", product);
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

    @Override
    public Optional<List<Review>> findPendingReviews() {
        String cypherQuery = "MATCH (r:Review) WHERE r.approvalStatus = 'pending' RETURN r";
        Map<String, Object> params = Map.of("approvalStatus", "pending");
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

    @Override
    public Optional<List<Review>> findActiveReviews() {
        String cypherQuery = "MATCH (r:Review) WHERE r.approvalStatus = 'active' RETURN r";
        Map<String, Object> params = Map.of("approvalStatus", "active");
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

    @Override
    public Optional<List<Review>> findByProductIdStatus(Product product, String status) {
        String cypherQuery = "MATCH (r:Review)-[:REVIEWS]->(p:Product) WHERE p.productId = $productId AND r.approvalStatus = $status RETURN r";
        Map<String, Object> params = Map.of("productId", product.getProductID(), "status", status);
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

    @Override
    public Optional<List<Review>> findByUserId(User user) {
        String cypherQuery = "MATCH (r:Review)-[:REVIEWS]->(u:User) WHERE u.userId = $userId RETURN r";
        Map<String, Object> params = Map.of("userId", user.getUserId());
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

    @Override
    public Optional<List<Review>> findReviewsVotedByUserId(Long userId) {
        String cypherQuery = "MATCH (r:Review)-[:UPVOTED_BY]->(u:User) WHERE u.id = $userId RETURN r";
        Map<String, Object> params = Map.of("userId", userId);
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }

  /*  @Override
    public Optional<List<Review>> findAllReviews() {
        String cypherQuery = "MATCH (r:Review) RETURN r";
        Map<String, Object> params = Map.of();
        Class<Review> domainClass = Review.class;
        List<Review> reviews = neo4JRepo.findAll(cypherQuery, params, domainClass);
        return Optional.of(reviews);
    }*/
}
