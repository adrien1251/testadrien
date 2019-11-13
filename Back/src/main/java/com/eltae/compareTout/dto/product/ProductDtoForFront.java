package com.eltae.compareTout.dto.product;

import com.eltae.compareTout.dto.CriteriaProductDto;
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
    private List<CriteriaProductDto> criteriaDtoList;

    public ProductDtoForFront clone() throws CloneNotSupportedException {
        return (ProductDtoForFront) super.clone();
    }

}