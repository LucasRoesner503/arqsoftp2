package com.arqsoft.project2.acmeProducts.repositories.mongo;


import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.property.EntityNotFoundException;
import com.arqsoft.project2.acmeProducts.repositories.ProductRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MongoProductRepositoryImp extends MongoBaseRepository<Product,Long> implements ProductRepository {


    public MongoProductRepositoryImp() {
        super(Product.class);
    }

    public MongoProductRepositoryImp(MongoTemplate mongoTemplate) {
        super(Product.class, mongoTemplate);
    }

    @Override
    public List<Product> findByDesignation(String designation) {
        List<Product> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        return all.stream().filter(p -> p.getDesignation().equals(designation)).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        List<Product> all = new ArrayList<>();
        this.findAll().forEach(all::add);
        for (Product p : all) {
            if (p.getSku().equals(sku)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> getCatalog() {
        return Optional.empty();
    }

    @Override
    public void deleteBySku(String sku) {
        Optional<Product> p = findBySku(sku);
        if(p.isPresent()){
            this.delete(p.get());
        }else{
            throw new EntityNotFoundException("Mongo Product Delete By SKU, Product not found");
        }

    }

    @Override
    public Product updateBySku(String sku) {
        return null;
    }
}
