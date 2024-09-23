package br.helis.architecture.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.model.Product;
import br.helis.architecture.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

     public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    @Transactional
    public void save(Product product) {  
        productRepository.save(product);     
    }

    @Override
    @Transactional
    public void update(Product product) throws ProductNotFoundException {
        if(Objects.nonNull(findById(product.getId()))) {
            productRepository.update(product);
        }
    }

    @Override
    public void deleteById(Long id) throws ProductNotFoundException {
        if(Objects.nonNull(findById(id))) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

}
