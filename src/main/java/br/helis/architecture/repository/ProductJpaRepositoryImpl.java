package br.helis.architecture.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.helis.architecture.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProductJpaRepositoryImpl implements ProductRepository {
 

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));

    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(Product.class, id));
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public List<Product> findAll() {
       return entityManager.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Product").executeUpdate();
    }
    
}
