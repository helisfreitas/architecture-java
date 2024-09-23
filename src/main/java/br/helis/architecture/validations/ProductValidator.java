package br.helis.architecture.validations;

import java.math.BigDecimal;

import br.com.fluentvalidator.AbstractValidator;
import static br.com.fluentvalidator.predicate.ComparablePredicate.*;
import br.helis.architecture.model.Product;

public class ProductValidator extends AbstractValidator<Product> {    
  

    @Override
    public void rules() {
        ruleFor(Product::getPrice)
        .must(greaterThan(BigDecimal.ZERO))
        .withMessage("age must be greater than to 0")  
        .withFieldName("price")     
        .must(lessThanOrEqual(new BigDecimal("100")))
        .withMessage("age must be less than or equal to 100")
        .withFieldName("price");
    }
    
}
