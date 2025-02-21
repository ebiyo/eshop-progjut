package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        // Test the main method by calling it with null args
        // This ensures the method can be called without throwing exceptions
        EshopApplication.main(new String[]{});
    }
}
