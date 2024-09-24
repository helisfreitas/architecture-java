package br.helis.architecture.api.model;


import java.io.Serializable;
import java.math.BigDecimal;

import br.helis.architecture.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

     private Category category;
    
    private BigDecimal price;

    private int stock;

    public ProductRequest(String name, Category  category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;   
    }

    public br.helis.architecture.model.Product toModel() {
        br.helis.architecture.model.Product model = new br.helis.architecture.model.Product();
        model.setName(name);
        model.setCategory(category);
        model.setPrice(price);
        model.setStock(stock);
        return model;
    }

    public static ProductRequest fromModel(br.helis.architecture.model.Product product) {
        ProductRequest product2 = new ProductRequest();
        product2.setName(product.getName());
        product2.setCategory(product.getCategory());
        product2.setPrice(product.getPrice());
        product2.setStock(product.getStock());
        return product2;
    }
   

}

