package com.arqsoft.project2.acmeAggregatedRatings.repositories.neo4j;



import com.arqsoft.project2.acmeAggregatedRatings.model.AggregatedRating;
import com.arqsoft.project2.acmeAggregatedRatings.model.Product;
import com.arqsoft.project2.acmeAggregatedRatings.repositories.AggregatedRatingRepository;

import java.util.Map;
import java.util.Optional;

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
