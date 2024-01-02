package services;


import com.arqsoft.project2.acmeProducts.model.Product;
import com.arqsoft.project2.acmeProducts.repositories.ProductRepository;
import com.arqsoft.project2.acmeProducts.services.ProductServiceImpl;
import com.arqsoft.project2.acmeProducts.utilities.SKUAlgorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import utilities.TestDataInitializer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ProductServiceTests.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SKUAlgorithms skuAlgorithms;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Product product = tdi.createSampleProduct();

    @Test
    void testGetProductBySku() {


        // Mock the repository method
        Mockito.when(productRepository.findBySku(product.sku)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductBySku(product.sku);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void testGetProductBySku_NotFound() {

        // Mock the repository method
        Mockito.when(productRepository.findBySku(product.sku)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = productService.getProductBySku(product.sku);

        // Assert
        assertFalse(result.isPresent());
    }

}
