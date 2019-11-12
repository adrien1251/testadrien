package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryCriteriaRepository extends JpaRepository<CategoryCriteria, CriteriaProductPK> {
    List<CategoryCriteria> findAll();

}

