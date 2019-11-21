package com.eltae.compareTout.dto.supplier;

import com.eltae.compareTout.dto.user.UserInscriptionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierInscriptionDto extends UserInscriptionDto implements Cloneable {

    private String webSite;
    private String siret;


    public SupplierInscriptionDto clone() throws CloneNotSupportedException {
        return (SupplierInscriptionDto) super.clone();
    }
}
