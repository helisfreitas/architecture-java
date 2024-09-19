package br.helis.architecture.validations;

import java.math.BigDecimal;

import br.helis.architecture.model.Product;

public class ProductValidator implements Validator {
    
    private Product product;

    public ProductValidator(Product product) {
        this.product = product;
    }

    @Override
    public boolean isValid() {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }

        if(product.getCategory() == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }

        if(product.getPrice() == null  || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {  
            throw new IllegalArgumentException("Product price cannot be zero or negative");
        }
        
        return true;
    }
    
}
