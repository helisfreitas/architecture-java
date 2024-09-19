package br.helis.architecture.exceptions;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    } 

}
