package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateWithoutId() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
        assertFalse(savedProduct.getProductId().isEmpty());
    }

    @Test
    void testCreateWithEmptyId() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
        assertFalse(savedProduct.getProductId().isEmpty());
    }

    @Test
    void testFindById_Exists() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        Optional<Product> found = productRepository.findById("test-id");

        assertTrue(found.isPresent());
        assertEquals("Test Product", found.get().getProductName());
    }

    @Test
    void testFindById_DoesNotExist() {
        Optional<Product> found = productRepository.findById("non-existent-id");
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdate_Success() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Original Name");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("test-id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }

    @Test
    void testUpdate_ProductNotFound() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Test Name");

        Product result = productRepository.update(nonExistentProduct);

        assertNull(result);
    }

    @Test
    void testDeleteById_Success() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        productRepository.create(product);

        boolean deleted = productRepository.deleteById("test-id");

        assertTrue(deleted);
        assertFalse(productRepository.findById("test-id").isPresent());
    }

    @Test
    void testDeleteById_NonExistent() {
        boolean deleted = productRepository.deleteById("non-existent-id");
        assertFalse(deleted);
    }
}