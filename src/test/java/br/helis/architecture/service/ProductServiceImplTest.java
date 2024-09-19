package br.helis.architecture.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.model.Category;
import br.helis.architecture.model.Product;
import br.helis.architecture.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Optional;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

   
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnErrorWhenProductIdNotFound() {
        // Given
        Long nonExistentId = 999L;
        Mockito.when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
    
        // When
        try {
            productService.findById(nonExistentId);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            // Then
            assertThat(e).isInstanceOf(ProductNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("Product not found with id: " + nonExistentId);
        }
    }

    
    @Test
    void shouldReturnErrorWhenProductIdNotFoundWhenDelete() {
        // Given
        Long nonExistentId = 999L;
        Mockito.when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
    
        // When
        try {
            productService.deleteById(nonExistentId);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            // Then
            assertThat(e).isInstanceOf(ProductNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("Product not found with id: " + nonExistentId);
        }
    }

    @Test
    void shouldReturnErrorWhenProductNameIsNullOrEmpty() {
        // Given
        String nullName = null;
        String emptyName = "";
        Product productWithNullName = new Product(1L, nullName, Category.MOBILE,  new BigDecimal("10.00"));
        Product productWithEmptyName = new Product(1L, emptyName, Category.MOBILE,  new BigDecimal("10.00"));;
    
        // When
        try {
            productService.save(productWithNullName);
            productService.save(productWithEmptyName);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            // Then
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            assertThat(e.getMessage()).contains("Product name must not be null or empty");
        }
    }
}