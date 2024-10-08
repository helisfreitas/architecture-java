package br.helis.architecture.products.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.notifications.entity.Outbox;
import br.helis.architecture.notifications.repository.OutboxRepository;
import br.helis.architecture.products.entity.Product;
import br.helis.architecture.products.repository.ProductRepository;
import br.helis.architecture.util.JsonHelper;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private RedisService redisService;

    private JsonHelper jsonHelper;

    private OutboxRepository outboxRepository;

    public ProductServiceImpl(ProductRepository productRepository, RedisService redisService, JsonHelper jsonHelper, OutboxRepository outboxRepository) {
        this.productRepository = productRepository;
        this.redisService = redisService;
        this.jsonHelper = jsonHelper;
        this.outboxRepository = outboxRepository;
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {
        Optional<Product> product = Optional.ofNullable(redisService.get(id.toString())).map(j -> jsonHelper.jsonToObject(j, Product.class));
        if(product.isPresent()) {
            return product.get();
        }
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    @Transactional
    public void save(Product product) {  
        productRepository.save(product);     
        String objectToJson = jsonHelper.objectToJson(product);
        redisService.save(product.getId().toString(), objectToJson);
        outboxRepository.save(new Outbox(jsonHelper.objectToJson(product)));
    }

    @Override
    @Transactional
    public void update(Product product) throws ProductNotFoundException {
        if(Objects.nonNull(findById(product.getId()))) {
            product.setUpdateAt(LocalDate.now());
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
