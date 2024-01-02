package com.arqsoft.project2.acmeAggregatedRatings.model;




import com.arqsoft.project2.acmeAggregatedRatings.repositories.Idget;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class AggregatedRating implements Idget<Long> {

    @Id
    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.springframework.data.neo4j.core.schema.GeneratedValue
    private Long aggregatedId;

    @Column()
    private double average;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Product product;

    protected AggregatedRating() {}

    public AggregatedRating(double average, Product product) {
        this.average = average;
        this.product = product;
        generateAggregateID();
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void generateAggregateID(){
        if(this.aggregatedId == null){
            long fullID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            // Convert the long to a string and get the last 13 characters
            String idString = String.valueOf(fullID);
            int length = idString.length();
            if (length > 13) {
                idString = idString.substring(length - 13);
            }
            this.aggregatedId = Long.parseLong(idString);        }
    }


    public Long getAggregatedId() {
        return aggregatedId;
    }

    @Override
    public Long getId() {
        return this.aggregatedId;
    }
}
