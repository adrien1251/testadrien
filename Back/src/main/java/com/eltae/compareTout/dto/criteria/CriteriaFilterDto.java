package com.eltae.compareTout.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaFilterDto implements Serializable {
    private Long idCriteria;
    private List<String> value;
    private Double minValue;
    private Double maxValue;
}
