package com.eltae.compareTout.dto.product;

import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.Supplier;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDtoForFront implements Cloneable {
    private Long id;
    private String name;
    private String description;
    private String supplierLink;
    private String imageLink;
    private List<CriteriaProductDto> criteriaDtoList;

    public ProductDtoForFront clone() throws CloneNotSupportedException {
        return (ProductDtoForFront) super.clone();
    }

}