package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditTest {
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();  // Initialize repository
        product = new Product();
        product.setProductId("12345");
        product.setProductName("Old Product Name");
        product.setProductQuantity(10);
        productRepository.create(product);  // Add product
    }

    @Test
    void testEditProduct() {
        // Update product details
        Product updatedProduct = new Product();
        updatedProduct.setProductId("12345"); // Same ID
        updatedProduct.setProductName("New Product Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);

        // Check if update was successful
        assertNotNull(result, "Product should be updated");
        assertEquals("New Product Name", result.getProductName(), "Product name should be updated");
        assertEquals(20, result.getProductQuantity(), "Product quantity should be updated");
    }

    @Test
    void testEditNonExistingProduct() {
        Product nonExistingProduct = new Product();
        nonExistingProduct.setProductId("99999"); // Non-existent ID
        nonExistingProduct.setProductName("Non-existing Product");
        nonExistingProduct.setProductQuantity(30);

        Product result = productRepository.update(nonExistingProduct);

        assertNull(result, "Updating a non-existing product should return null");
    }
}
