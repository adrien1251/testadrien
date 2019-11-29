package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.CriteriaProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaProductRepository extends JpaRepository<CriteriaProduct, CriteriaProductPK>, JpaSpecificationExecutor<CriteriaProduct> {
    List<CriteriaProduct> findAll();
    List<CriteriaProduct> findDistinctByPk_CriteriaAndAndPk_ProductCategory(Criteria criteria, Category category);
}

