package com.eltae.compareTout.dto;

import com.eltae.compareTout.entities.Supplier;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InsertionErrorsDto implements Cloneable {

    private Long id;
    private Integer lineNumber;
    private LocalDate insertionDate;
    private String description;
    private Supplier supplier;

    public InsertionErrorsDto clone() throws CloneNotSupportedException {
        return (InsertionErrorsDto) super.clone();
    }

}