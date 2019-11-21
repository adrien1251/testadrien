package com.eltae.compareTout.dto.product;

import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.Category;
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
    private Category category;
    private List<CriteriaProductDto> criteriaProducts;

    public ProductDto clone() throws CloneNotSupportedException {
        return (ProductDto) super.clone();
    }

}