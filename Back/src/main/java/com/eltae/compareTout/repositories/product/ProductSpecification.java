package com.eltae.compareTout.repositories.product;

import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.OurCriteriaBuilder;
import com.eltae.compareTout.entities.Product;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class ProductSpecification {
    private ProductCriteria productCriteria;

    public Predicate toPredicate(Join<Product, CriteriaProduct> join, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(productIsInCategory(join, criteriaBuilder));
        predicateList.add(productHaveCriteriaList(join));
        predicateList.add(productHaveGreatCriteria(join, criteriaBuilder));

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private Predicate productHaveGreatCriteria(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (OurCriteriaBuilder ourCriteriaBuilder : productCriteria.getCriteriaBuilders()) {
            switch (ourCriteriaBuilder.getCriteria().getType()) {
                case FLOAT:
                    predicateList.add(criteriaIsDecimale(join, criteriaBuilder, ourCriteriaBuilder));
                    break;
                case INT:
                    predicateList.add(criteriaIsDecimale(join, criteriaBuilder, ourCriteriaBuilder));
                    break;
                case STRING:
                    predicateList.add(criteriaIsString(join, criteriaBuilder, ourCriteriaBuilder));
            }
        }
        return criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
    }

    private Predicate criteriaIsString(Join<Product, CriteriaProduct> join,  CriteriaBuilder criteriaBuilder, OurCriteriaBuilder ourCriteriaBuilder) {
        return criteriaBuilder.like(join.get("value"), "%" + ourCriteriaBuilder.getValue() + "%");
    }

    private Predicate criteriaIsDecimale(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder, OurCriteriaBuilder ourCriteriaBuilder) {
        if(ourCriteriaBuilder.getValue() != null){
            return criteriaBuilder.equal(join.get("value"), ourCriteriaBuilder.getValue());
        }

        if(ourCriteriaBuilder.getMinValue() != null && ourCriteriaBuilder.getMaxValue() != null){
            return criteriaBuilder.between(join.get("value").as(Double.class), ourCriteriaBuilder.getMinValue(), ourCriteriaBuilder.getMaxValue());
        } else if(ourCriteriaBuilder.getMinValue() != null) {
            return criteriaBuilder.greaterThanOrEqualTo(join.get("value").as(Double.class), ourCriteriaBuilder.getMinValue());
        }

        return criteriaBuilder.lessThanOrEqualTo(join.get("value").as(Double.class), ourCriteriaBuilder.getMaxValue());
    }

    private Predicate productIsInCategory(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(join.get("pk").get("product").get("category"), productCriteria.getCategory());
    }

    private Predicate productHaveCriteriaList(Join<Product, CriteriaProduct> join) {
        List<Criteria> criteria = new ArrayList<>();
        for (OurCriteriaBuilder ourCriteriaBuilder : productCriteria.getCriteriaBuilders()) {
            criteria.add(ourCriteriaBuilder.getCriteria());
        }
        return join.get("pk").get("criteria").in(criteria);
    }
}
