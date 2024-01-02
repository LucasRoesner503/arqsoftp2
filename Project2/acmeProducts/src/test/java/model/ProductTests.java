package model;


import com.arqsoft.project2.acmeProducts.model.Product;
import org.junit.jupiter.api.Test;
import utilities.TestDataInitializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
public class ProductTests {


    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Product product = tdi.createSampleProduct();

    @Test
    void testConstructorWithAllAttributes(){

        //assert
        assertEquals(product.sku, product.getSku());
        assertEquals(TestDataInitializer.designation, product.getDesignation());
        assertEquals(TestDataInitializer.description, product.getDescription());

    }

    @Test
    void testSetSku(){
        product.setSku(TestDataInitializer.sku);
        assertEquals(product.sku, product.getSku());
    }

    @Test
    void testSetDescription(){
        String description = "New description";
        product.setDescription(description);

        assertEquals(description,product.getDescription());

    }

    @Test
    void testSetDesignation(){
        String designation = "New designation";
        product.setDesignation(designation);

        assertEquals(designation,product.getDesignation());

    }

    @Test
    void testGenerateProductID() {

        // Act
        product.generateProductID();

        // Assert
        assertNotNull(product.getProductID());
    }

    @Test
    void testUpdateProduct() {

        Product newProduct = new Product(TestDataInitializer.sku, "Designation 2", "Description 2");

        // Act
        product.updateProduct(newProduct);

        // Assert
        assertEquals(product.getDesignation(), newProduct.getDesignation());
        assertEquals(product.getDescription(), newProduct.getDescription());
    }



}
