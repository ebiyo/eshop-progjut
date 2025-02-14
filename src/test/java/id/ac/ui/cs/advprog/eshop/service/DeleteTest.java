package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTest {
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();  // Initialize repository
        product = new Product();
        product.setProductId("12345");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);  // Add product
    }

    @Test
    void testDeleteExistingProduct() {
        boolean result = productRepository.deleteById("12345");

        assertTrue(result, "Deleting an existing product should return true");

        Optional<Product> deletedProduct = productRepository.findById("12345");
        assertFalse(deletedProduct.isPresent(), "Deleted product should not be found");
    }

    @Test
    void testDeleteNonExistingProduct() {
        boolean result = productRepository.deleteById("99999");

        assertFalse(result, "Deleting a non-existing product should return false");
    }
}
