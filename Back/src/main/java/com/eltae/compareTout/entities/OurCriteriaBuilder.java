package com.eltae.compareTout.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OurCriteriaBuilder {
    private Criteria criteria;
    private String value;
    private Double minValue;
    private Double maxValue;
}