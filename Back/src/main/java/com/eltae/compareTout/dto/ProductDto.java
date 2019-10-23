package com.eltae.compareTout.dto;

import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDto implements Cloneable {
    private Long id;
    private String name;
    private List<Long> criteriaIds;

    public ProductDto clone() throws CloneNotSupportedException {
        return (ProductDto) super.clone();
    }

}