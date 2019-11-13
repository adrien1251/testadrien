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
public class ShortProductDto implements Cloneable {
    private Long id;
    private String name;
    private long category;
    private List<CriteriaProductDto> criteriaProducts;

    public ShortProductDto clone() throws CloneNotSupportedException {
        return (ShortProductDto) super.clone();
    }

}