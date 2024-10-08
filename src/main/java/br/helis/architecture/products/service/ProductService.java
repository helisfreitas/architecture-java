package br.helis.architecture.products.service;

import java.util.List;

import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.products.entity.Product;

public interface ProductService {

    Product findById(Long id) throws ProductNotFoundException;

    void save(Product product);

    void update(Product product) throws ProductNotFoundException;

    void deleteById(Long id) throws ProductNotFoundException;

    List<Product> findAll();
    
}