package com.eltae.compareTout.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OurCriteria {
    private Criteria criteria;
    private List<String> value;
    private Double minValue;
    private Double maxValue;
}