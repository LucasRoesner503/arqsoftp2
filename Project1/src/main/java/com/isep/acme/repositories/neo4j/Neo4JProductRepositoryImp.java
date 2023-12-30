package com.isep.acme.repositories.neo4j;

import com.isep.acme.model.Product;
import com.isep.acme.repositories.ProductRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Neo4JProductRepositoryImp extends Neo4JBaseRepository<Product, Long> implements ProductRepository {

    public Neo4JProductRepositoryImp() {
        super(Product.class);
    }

    @Override
    public List<Product> findByDesignation(String designation) {
        String cypherQuery = "MATCH (p:Product) WHERE p.designation = $designation RETURN p";
        Map<String, Object> params = Map.of("designation", designation);
        Class<Product> domainClass = Product.class;
        return neo4JRepo.findOne(cypherQuery, params, domainClass).stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        String cypherQuery = "MATCH (p:Product) WHERE p.sku = $sku RETURN p";
        Map<String, Object> parameters = Map.of("sku", sku);
        Class<Product> domainType = Product.class;
        return neo4JRepo.findOne(cypherQuery, parameters, domainType);
    }

    @Override
    public Optional<Product> getCatalog() {
        return Optional.empty();
    }

    @Override
    public void deleteBySku(String sku) {

        Optional<Product> product = findBySku(sku);

        //delete by Id
        product.ifPresent(value -> neo4JRepo.deleteById(value.getProductID(), Product.class));

    }

    @Override
    public Product updateBySku(String sku) {
        return null;
    }

}
