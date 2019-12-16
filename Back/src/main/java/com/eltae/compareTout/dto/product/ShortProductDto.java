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
public class ShortProductDto implements Cloneable {
    private Long id;
    private String name;
    private long category;
    private String imageLink;
    private List<CriteriaProductDto> criteriaProducts;
    private Supplier supplier;

    public ShortProductDto clone() throws CloneNotSupportedException {
        return (ShortProductDto) super.clone();
    }

}