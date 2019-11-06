package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.CriteriaProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaProductRepository extends JpaRepository<CriteriaProduct, CriteriaProductPK> {
    List<CriteriaProduct> findAll();

}

