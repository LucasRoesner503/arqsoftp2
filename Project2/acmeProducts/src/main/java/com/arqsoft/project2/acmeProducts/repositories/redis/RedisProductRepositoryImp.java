package com.arqsoft.project2.acmeProducts.repositories.redis;


import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.repositories.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RedisProductRepositoryImp extends RedisBaseRepository<Product,Long> implements ProductRepository {


   // private final HashOperations<String, String, Product> hashOperations;

    public RedisProductRepositoryImp(){
        super(Product.class);

    }

    public RedisProductRepositoryImp(RedisTemplate<String, Product> redisTemplate){
        super(Product.class, redisTemplate);
       // this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public List<Product> findByDesignation(String designation) {
        List<Product> result = new ArrayList<>();
        Iterable<Product> allProducts = findAll();

        for (Product product : allProducts) {
            if (product.getDesignation().equals(designation)) {
                result.add(product);
            }
        }

        return result;
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        Optional<Product> result;
        Iterable<Product> allProducts = findAll();

        for (Product product : allProducts) {
            if (product.getSku().equals(sku)) {
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Product> getCatalog() {
        throw new UnsupportedOperationException("Get Catalog - isn't this the same as a find all?");
    }

    @Override
    public void deleteBySku(String sku) {
        Optional<Product> product = this.findBySku(sku);
        product.ifPresent(this::delete);
    }

    @Override
    public Product updateBySku(String sku) {
        // what is this supposed to update?
        throw new UnsupportedOperationException("Update By SKU - what is this supposed to update?");
    }


}
