package com.eltae.compareTout.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdminDto extends UserDto implements Cloneable  {

    private String phoneNum;

    public AdminDto clone() throws CloneNotSupportedException {
        return (AdminDto) super.clone();
    }

}