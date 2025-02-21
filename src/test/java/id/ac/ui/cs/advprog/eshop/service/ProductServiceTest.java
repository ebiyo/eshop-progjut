package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        when(productRepository.create(product)).thenReturn(product);

        // Act
        Product created = productService.create(product);

        // Assert
        assertNotNull(created);
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAllProducts() {
        // Arrange
        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Test Product 2");
        product2.setProductQuantity(20);

        List<Product> productList = Arrays.asList(product, product2);
        when(productRepository.findAll()).thenReturn(productList.iterator());

        // Act
        List<Product> found = productService.findAll();

        // Assert
        assertNotNull(found);
        assertEquals(2, found.size());
        assertEquals(product.getProductName(), found.get(0).getProductName());
        assertEquals(product2.getProductName(), found.get(1).getProductName());
        verify(productRepository).findAll();
    }

    @Test
    void testFindById_Found() {
        // Arrange
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        // Act
        Product found = productService.findById(product.getProductId());

        // Assert
        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
        assertEquals(product.getProductName(), found.getProductName());
        verify(productRepository).findById(product.getProductId());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act
        Product found = productService.findById(nonExistentId);

        // Assert
        assertNull(found);
        verify(productRepository).findById(nonExistentId);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(15);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        // Act
        Product result = productService.update(updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository).update(updatedProduct);
    }

    @Test
    void testDeleteProduct_Successful() {
        // Arrange
        when(productRepository.deleteById(product.getProductId())).thenReturn(true);

        // Act
        boolean result = productService.deleteProduct(product.getProductId());

        // Assert
        assertTrue(result);
        verify(productRepository).deleteById(product.getProductId());
    }

    @Test
    void testDeleteProduct_Unsuccessful() {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(productRepository.deleteById(nonExistentId)).thenReturn(false);

        // Act
        boolean result = productService.deleteProduct(nonExistentId);

        // Assert
        assertFalse(result);
        verify(productRepository).deleteById(nonExistentId);
    }

    @Test
    void testFindAll_EmptyList() {
        // Arrange
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>().iterator());

        // Act
        List<Product> found = productService.findAll();

        // Assert
        assertNotNull(found);
        assertTrue(found.isEmpty());
        verify(productRepository).findAll();
    }
}
