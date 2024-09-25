package br.helis.architecture.products.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated
    private Category category;
    
    @Positive
    @DecimalMax(value="9999999.99")
    private BigDecimal price;

    @Getter
    private int stock;

    @NotNull
    @PastOrPresent
    private LocalDate createAt;
    
    @PastOrPresent
    private LocalDate updateAt;

    public Product () {
        this.createAt = LocalDate.now();
    }
 
    public Product(Long id, String name, Category  category, BigDecimal price) {
        this();
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        
    }

    public Product(String name, Category  category, BigDecimal price) {
        this(null, name, category, price);
    }
    
    public void decreaseStock(int quantity) {
        if (quantity > 0) {
            this.stock -= quantity;
        }
    }

    public void increaseStock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
        }
    }
}

