package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findAllByCategoryId(long idCategory);
    List<Product> findProductById(long idProduct);
}