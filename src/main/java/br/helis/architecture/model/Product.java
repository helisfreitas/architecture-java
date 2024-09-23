package br.helis.architecture.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated
    private Category category;
    
    private BigDecimal price;

    @Getter
    private int stock;

    private LocalDate createAt;
    
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
        if (category == null) {
            return null;
        }  else  {
            return category.getName();
        }
    }

}

