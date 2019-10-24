package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();


    List<Product> findAllByCategoryId(long idCategory);
}