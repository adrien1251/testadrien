package com.eltae.compareTout.dto;
import com.eltae.compareTout.entities.TypeCriteria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CriteriaDto implements  Cloneable {

    private Long id;
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private TypeCriteria type;
    private String unit;
    private boolean isMandatory;


    public CriteriaDto clone() throws CloneNotSupportedException {
        return (CriteriaDto) super.clone();
    }
}