package com.eltae.compareTout.repositories.product;

import com.eltae.compareTout.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private ProductCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null; //TODO le predicate
    }
}
