package br.helis.architecture.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.model.Product;
import br.helis.architecture.repository.ProductRepository;
import br.helis.architecture.util.JsonHelper;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private RedisService redisService;

    private JsonHelper jsonHelper;

     public ProductServiceImpl(ProductRepository productRepository, RedisService redisService, JsonHelper jsonHelper) {
        this.productRepository = productRepository;
        this.redisService = redisService;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {
        Product product = Optional.ofNullable(redisService.get(id.toString())).map(j -> jsonHelper.jsonToObject(j, Product.class)).get();
        if(product != null) {
            return product;
        }
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    @Transactional
    public void save(Product product) {  
        productRepository.save(product);     
        redisService.save(product.getName(), jsonHelper.objectToJson(product));
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
