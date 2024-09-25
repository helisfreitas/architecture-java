package br.helis.architecture.products.api.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.helis.architecture.products.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Category category;
    
    private BigDecimal price;

    private int stock;

    private LocalDate createAt;
    
    private LocalDate updateAt;

    public ProductResponse(Long id, String name, Category  category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createAt = LocalDate.now();

    }

    public ProductResponse(String name, Category  category, BigDecimal price) {
        this(null, name, category, price);
    }

    public br.helis.architecture.products.entity.Product toModel() {
        br.helis.architecture.products.entity.Product model = new br.helis.architecture.products.entity.Product();
        model.setId(id);
        model.setName(name);
        model.setCategory(category);
        model.setPrice(price);
        model.setStock(stock);
        model.setCreateAt(createAt);
        model.setUpdateAt(updateAt);
        return model;
    }

    public static ProductResponse fromModel(br.helis.architecture.products.entity.Product product) {
        ProductResponse product2 = new ProductResponse();
        product2.setId(product.getId());
        product2.setName(product.getName());
        product2.setCategory(product.getCategory());
        product2.setPrice(product.getPrice());
        product2.setStock(product.getStock());
        product2.setCreateAt(product.getCreateAt());
        product2.setUpdateAt(product.getUpdateAt());
        return product2;
    }
   

}

