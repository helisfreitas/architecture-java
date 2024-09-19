package br.helis.architecture.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated
    private Category category;
    
    @Positive
    @Digits(integer= 12, fraction=2)
    private BigDecimal price;

    @Getter
    private int stock;

    @PastOrPresent
    private LocalDate createAt;
    
    @PastOrPresent
    private LocalDate updateAt;

    public Product(Long id, String name, Category  category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createAt = LocalDate.now();

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

    public String getCategoryName() {
       return category.getName();
    }

}

