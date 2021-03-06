package com.eltae.compareTout.dto.criteria;

import com.eltae.compareTout.entities.TypeCriteria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CriteriaProductDto implements Cloneable {
    private Long id;
    private String value;
    private String criteriaName;
    private String criteriaUnit;
    private Boolean isMandatory;
    private TypeCriteria type;

    public CriteriaProductDto clone() throws CloneNotSupportedException {
        return (CriteriaProductDto) super.clone();
    }

}