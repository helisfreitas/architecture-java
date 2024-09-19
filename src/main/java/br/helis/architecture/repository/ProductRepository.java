package br.helis.architecture.repository;

import java.util.List;
import java.util.Optional;

import br.helis.architecture.model.Product;


public interface ProductRepository {

    void save(Product product); 

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    void update(Product product);

    List<Product> findAll();

    void deleteAll();

    
}
