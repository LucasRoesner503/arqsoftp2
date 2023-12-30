package com.isep.acme.model;



import com.isep.acme.repositories.Idget;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Rating implements Idget<Long> {

    @Id
    @org.springframework.data.neo4j.core.schema.Id
    @org.springframework.data.neo4j.core.schema.GeneratedValue
    @MongoId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRating;

    @Version
    private long version;

    @Column(nullable = false)
    private Double rate;

    public Rating(){
        generateRatingID();
    }

    public Rating(Long idRating, long version, Double rate) {
        this.idRating = Objects.requireNonNull(idRating);
        this.version = Objects.requireNonNull(version);
        setRate(rate);
    }

    public void generateRatingID(){
        if(this.idRating == null){
            long fullID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            // Convert the long to a string and get the last 13 characters
            String idString = String.valueOf(fullID);
            int length = idString.length();
            if (length > 13) {
                idString = idString.substring(length - 13);
            }
            this.idRating = Long.parseLong(idString);
        }

    }

    public Rating(Double rate) {
        generateRatingID();
        setRate(rate);
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public Long getId() {
        return this.idRating;
    }
}
