package com.eltae.compareTout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaFilterDto implements Serializable {
    private Long idCriteria;
    private String value;
    private Double minValue;
    private Double maxValue;
}
