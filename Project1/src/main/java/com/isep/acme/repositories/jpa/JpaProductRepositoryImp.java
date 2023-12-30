/*package com.isep.acme.repositories.jpa;

import com.isep.acme.model.Product;
import com.isep.acme.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

public class JpaProductRepositoryImp extends JpaBaseRepository<Product,Long> implements ProductRepository {

    public JpaProductRepositoryImp(){
        super(Product.class, "Product");
    }


    @Override
    public List<Product> findByDesignation(String designation) {
        return null;
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.empty();
    }

   /* @Override
    public Optional<Product> getCatalog() {
        return Optional.empty();
    }

    @Override
    public void deleteBySku(String sku) {

    }

    @Override
    public Product updateBySku(String sku) {
        return null;
    }
}
*/