package com.isep.acme.repositories.neo4j;

import com.isep.acme.model.AggregatedRating;
import com.isep.acme.model.Product;
import com.isep.acme.repositories.AggregatedRatingRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Neo4JAggregatedRatingRepositoryImp extends Neo4JBaseRepository<AggregatedRating,Long> implements AggregatedRatingRepository {

    public Neo4JAggregatedRatingRepositoryImp() {
        super(AggregatedRating.class);
    }

    @Override
    public Optional<AggregatedRating> findByProductId(Product product) {
        String cypherQuery = "MATCH (ar:AggregatedRating)-[:AggregatedRating]->(p:Product) WHERE p.productId = $productId RETURN ar";
        Map<String, Object> params = Map.of("productId", product.getProductID());
        Class<AggregatedRating> domainClass = AggregatedRating.class;
        return neo4JRepo.findOne(cypherQuery, params, domainClass);
    }
}
