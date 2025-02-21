package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        // Test GET /product/create
        String viewName = productController.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        // Test POST /product/create
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);

        verify(productService).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        // Test GET /product/list
        List<Product> productList = new ArrayList<>();
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);

        verify(model).addAttribute("products", productList);
        assertEquals("productList", viewName);
    }

    @Test
    void testEditProductPage_ProductExists() {
        // Test GET /product/edit/{id} when product exists
        Product product = new Product();
        when(productService.findById("testId")).thenReturn(product);

        String viewName = productController.editProductPage("testId", model);

        verify(model).addAttribute("product", product);
        assertEquals("editProduct", viewName);
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        // Test GET /product/edit/{id} when product doesn't exist
        when(productService.findById("nonexistentId")).thenReturn(null);

        String viewName = productController.editProductPage("nonexistentId", model);

        verify(model, never()).addAttribute(eq("product"), any(Product.class));
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPost() {
        // Test POST /product/edit
        Product product = new Product();
        String viewName = productController.editProductPost(product, model);

        verify(productService).update(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct_Success() {
        // Test GET /product/delete/{id} when successful
        when(productService.deleteProduct("testId")).thenReturn(true);

        String viewName = productController.deleteProduct("testId");

        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Test GET /product/delete/{id} when product not found
        when(productService.deleteProduct("nonexistentId")).thenReturn(false);

        String viewName = productController.deleteProduct("nonexistentId");

        assertEquals("redirect:/product/list?error=notfound", viewName);
    }
}