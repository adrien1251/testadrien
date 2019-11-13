package com.eltae.compareTout.repositories.product;

import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.OurCriteria;
import com.eltae.compareTout.entities.Product;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class CriteriaFilterSpecification {
    private CriteriaFilter criteriaFilter;

    public Predicate toPredicate(Join<Product, CriteriaProduct> join, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(productIsInCategory(join, criteriaBuilder));
        predicateList.add(productHaveCriteriaList(join));
        predicateList.add(productHaveGreatCriteria(join, criteriaBuilder));
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private Predicate productHaveGreatCriteria(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (OurCriteria ourCriteria : criteriaFilter.getCriterias()) {
            switch (ourCriteria.getCriteria().getType()) {
                case FLOAT:
                    predicateList.add(criteriaIsDecimale(join, criteriaBuilder, ourCriteria));
                    break;
                case INT:
                    predicateList.add(criteriaIsDecimale(join, criteriaBuilder, ourCriteria));
                    break;
                case STRING:
                    predicateList.add(criteriaIsString(join, criteriaBuilder, ourCriteria));
            }
        }
        return criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
    }

    private Predicate criteriaIsString(Join<Product, CriteriaProduct> join,  CriteriaBuilder criteriaBuilder, OurCriteria ourCriteria) {
        return criteriaBuilder.and(
                criteriaBuilder.equal(join.get("pk").get("criteria"), ourCriteria.getCriteria()),
                criteriaBuilder.like(join.get("value"), "%" + ourCriteria.getValue() + "%")
        );
    }

    private Predicate criteriaIsDecimale(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder, OurCriteria ourCriteria) {
        Predicate decimalPredicate;

        if(ourCriteria.getValue() != null){
            decimalPredicate = criteriaBuilder.equal(join.get("value"), ourCriteria.getValue());
        } else if(ourCriteria.getMinValue() != null && ourCriteria.getMaxValue() != null){
            decimalPredicate = criteriaBuilder.between(join.get("value").as(Double.class), ourCriteria.getMinValue(), ourCriteria.getMaxValue());
        } else if(ourCriteria.getMinValue() != null) {
            decimalPredicate = criteriaBuilder.greaterThanOrEqualTo(join.get("value").as(Double.class), ourCriteria.getMinValue());
        } else {
            decimalPredicate = criteriaBuilder.lessThanOrEqualTo(join.get("value").as(Double.class), ourCriteria.getMaxValue());
        }

        return criteriaBuilder.and(
                criteriaBuilder.equal(join.get("pk").get("criteria"), ourCriteria.getCriteria()),
                decimalPredicate
        );
    }

    private Predicate productIsInCategory(Join<Product, CriteriaProduct>  join, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(join.get("pk").get("product").get("category"), criteriaFilter.getCategory());
    }

    private Predicate productHaveCriteriaList(Join<Product, CriteriaProduct> join) {
        List<Criteria> criteria = new ArrayList<>();
        for (OurCriteria ourCriteria : criteriaFilter.getCriterias()) {
            criteria.add(ourCriteria.getCriteria());
        }
        return join.get("pk").get("criteria").in(criteria);
    }
}
