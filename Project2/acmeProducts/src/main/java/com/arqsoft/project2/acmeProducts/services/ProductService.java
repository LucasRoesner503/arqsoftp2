package com.arqsoft.project2.acmeProducts.services;



import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.model.ProductDTO;
import com.arqsoft.project2.acmeProducts.model.ProductDetailDTO;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDTO> findBySku(final String sku);

    Optional<Product> getProductBySku(final String sku );

    Iterable<ProductDTO> findByDesignation(final String designation);

    Iterable<ProductDTO> getCatalog();

    Iterable<ProductDTO> findByApproved();

    ProductDetailDTO getDetails(final String sku);

    ProductDTO create(final Product manager);

    ProductDTO updateBySku(final String sku, final Product product);

    ProductDTO approveProduct(final String sku);

    void deleteBySku(final String sku);
}
