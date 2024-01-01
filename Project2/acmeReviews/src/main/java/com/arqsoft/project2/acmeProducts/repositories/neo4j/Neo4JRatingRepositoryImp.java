package com.arqsoft.project2.acmeProducts.repositories.neo4j;



import com.arqsoft.project2.acmeProducts.model.Rating;
import com.arqsoft.project2.acmeProducts.repositories.RatingRepository;

import java.util.Map;
import java.util.Optional;

public class Neo4JRatingRepositoryImp extends Neo4JBaseRepository<Rating, Long> implements RatingRepository {

    public Neo4JRatingRepositoryImp() {
        super(Rating.class);
    }

    @Override
    public Optional<Rating> findByRate(Double rate) {

        String cypherQuery = "MATCH (r:Rating) WHERE r.rate = $rate RETURN r";
        Map<String, Object> params = Map.of("rate", rate);
        Class<Rating> domainClass = Rating.class;
        return neo4JRepo.findOne(cypherQuery, params, domainClass);
    }
}
