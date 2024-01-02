package com.arqsoft.project2.acmeAggregatedRatings.model;


import com.arqsoft.project2.acmeAggregatedRatings.repositories.Idget;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product implements Idget<Long> {

    @Id
    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;

    @Column(nullable = false, unique = true)
    public String sku;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int approved;
    /*
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> review = new ArrayList<Review>(); */

    public Product(){}

    public Product(final Long productID, final String sku) {
        this.productID = Objects.requireNonNull(productID);
        setSku(sku);
        this.approved = 0;
    }

    public Product(final Long productID, final String sku, final String designation, final String description) {
        this(productID, sku);
        setDescription(description);
        setDesignation(designation);
        this.approved = 0;
    }

    public Product(final String sku) {
        setSku(sku); this.approved = 0;
    }

    public Product(final String sku, final String designation, final String description) {
        this(sku);
        generateProductID();
        setDescription(description);
        setDesignation(designation);
        this.approved = 0;
    }

    public void setSku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU is a mandatory attribute of Product.");
        }
        if (sku.length() < 10 || sku.length() > 12) {
            throw new IllegalArgumentException("SKU must be 12 or 11 characters long.");
        }

        this.sku = sku;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation is a mandatory attribute of Product.");
        }
        if (designation.length() > 50) {
            throw new IllegalArgumentException("Designation must not be greater than 50 characters.");
        }
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is a mandatory attribute of Product.");
        }

        if (description.length() > 1200) {
            throw new IllegalArgumentException("Description must not be greater than 1200 characters.");
        }

        this.description = description;
    }

    public String getSku() {
        return sku;
    }


    public void updateProduct(Product p) {
        setDesignation(p.designation);
        setDescription(p.description);
    }

    public Long getProductID() {
        return productID;
    }

    public void generateProductID(){
        if(this.productID == null){
            long fullID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            // Convert the long to a string and get the last 13 characters
            String idString = String.valueOf(fullID);
            int length = idString.length();
            if (length > 13) {
                idString = idString.substring(length - 13);
            }
            this.productID = Long.parseLong(idString);        }
    }



    @Override
    public Long getId() {
        return this.productID;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }
/*
    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }
*/

}
