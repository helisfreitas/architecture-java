package br.helis.architecture.products.api.model;


import java.io.Serializable;
import java.math.BigDecimal;

import br.helis.architecture.products.entity.Category;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @NotNull
    private Category category;
    
    @NotNull
    @Positive
    @DecimalMax(value="99999.99")
    @Digits(integer = 15, fraction=2)
    private BigDecimal price;

    public ProductRequest(String name, Category  category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;   
    }

    public br.helis.architecture.products.entity.Product toModel() {
        br.helis.architecture.products.entity.Product model = new br.helis.architecture.products.entity.Product();
        model.setName(name);
        model.setCategory(category);
        model.setPrice(price);
       return model;
    }

    public static ProductRequest fromModel(br.helis.architecture.products.entity.Product product) {
        ProductRequest product2 = new ProductRequest();
        product2.setName(product.getName());
        product2.setCategory(product.getCategory());
        product2.setPrice(product.getPrice());
        return product2;
    }
   

}

