package com.eltae.compareTout.dto.supplier;

import com.eltae.compareTout.dto.user.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierDto extends UserDto implements Cloneable {

    private String webSite;
    private String siret;
    private LocalDate validationDate;


    public SupplierDto clone() throws CloneNotSupportedException {
        return (SupplierDto) super.clone();
    }
}
