package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    List<Category> findByParent_idIsNull();
    Optional<Category> findByName(String name);
}