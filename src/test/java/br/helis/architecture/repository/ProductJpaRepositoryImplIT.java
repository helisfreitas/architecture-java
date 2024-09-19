package br.helis.architecture.repository;

import br.helis.architecture.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Import(ProductJpaRepositoryImpl.class)
@Transactional
class ProductJpaRepositoryImplIT {


    @Autowired
    private ProductRepository productRepository;

    // Define the PostgreSQL container
    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("mydatabase")
            .withUsername("postgres")
            .withPassword("postgres");

    // Dynamically configure the Spring Boot datasource
    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDBContainer::getUsername);
        registry.add("spring.datasource.password", postgresDBContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresDBContainer::getDriverClassName);
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindProductById() {
        // Arrange: Create a new product
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.99"));
        product.setStock(10);

        // Act: Save the product
        productRepository.save(product);

        // Assert: Retrieve the product and check its properties
        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        assertThat(foundProduct.get().getPrice()).isEqualByComparingTo(new BigDecimal("10.99"));
    }

    @Test
    void shouldFindAllProducts() {
        // Arrange: Save multiple products
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(new BigDecimal("10.00"));
        product1.setStock(5);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(new BigDecimal("15.00"));
        product2.setStock(7);

        productRepository.save(product1);
        productRepository.save(product2);

        // Act: Retrieve all products
        List<Product> products = productRepository.findAll();

        // Assert: Check if the size and products are correct
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Product 1");
        assertThat(products.get(1).getName()).isEqualTo("Product 2");
    }

    @Test
    void shouldDeleteProductById() {
        // Arrange: Save a product
        Product product = new Product();
        product.setName("Product to Delete");
        product.setPrice(new BigDecimal("5.00"));
        product.setStock(1);
        productRepository.save(product);

        // Act: Delete the product
        productRepository.deleteById(product.getId());

        // Assert: Check that the product no longer exists
        assertThat(productRepository.findById(product.getId())).isNotPresent();
    }
}
