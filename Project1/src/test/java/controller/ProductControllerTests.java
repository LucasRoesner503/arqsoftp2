package controller;

import com.isep.acme.SecurityConfig;
import com.isep.acme.controllers.ProductController;
import com.isep.acme.model.Product;
import com.isep.acme.model.ProductDTO;
import com.isep.acme.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utilities.TestDataInitializer;
import java.util.*;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@SpringBootTest(classes = ProductController.class)
@AutoConfigureMockMvc
@EnableWebMvc
@WithMockUser
public class ProductControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final Product product = tdi.createSampleProduct();

    @Test
    public void testGetCatalog() throws Exception {
        // Arrange
        List<ProductDTO> productList = new ArrayList<>();
        when(productService.getCatalog()).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(productList.size())));


    }

    @Test
    public void testGetProductBySku() throws Exception {

        ProductDTO p = new ProductDTO(product.sku,product.getDesignation());
        when(productService.findBySku(product.sku)).thenReturn(Optional.of(p));

        // Act & Assert
        mockMvc.perform(get("/products/{sku}",product.sku))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(p.getSku()));
    }

    @Test
    public void testGetProductBySkuNotFound() throws Exception {
        // Arrange
        String sku = "123";
        when(productService.findBySku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/products/{sku}", sku))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAllByDesignation() throws Exception {

        List<ProductDTO> productList = new ArrayList<>();
        when(productService.findByDesignation(product.getDesignation())).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(get("/products/designation/{designation}", product.getDesignation()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(productList.size())));
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        ProductDTO p = new ProductDTO(product.sku,product.getDesignation());
        when(productService.create(any(Product.class))).thenReturn(p);

        // Act & Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(p))
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value(p.getSku()));
    }

    @Test
    public void testCreateConflict() throws Exception {
        // Arrange
        when(productService.create(any(Product.class))).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT,"Product must have a unique SKU."));

        // Act & Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product))
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdate() throws Exception {

        ProductDTO product = new ProductDTO(this.product.sku,this.product.getDesignation());
        when(productService.updateBySku(eq(this.product.sku), any(Product.class))).thenReturn(product);

        // Act & Assert
        mockMvc.perform(patch("/products/{sku}", this.product.sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product))
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(product.getSku()));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        // Arrange
        String sku = "123";
        when(productService.updateBySku(eq(sku), any(Product.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(patch("/products/{sku}", sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product))
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        String sku = "123";
        //todo
        // Act & Assert
        mockMvc.perform(delete("/products/{sku}", sku)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNoContent());
    }

}
