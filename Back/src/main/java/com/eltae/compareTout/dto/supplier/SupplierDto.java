package com.eltae.compareTout.dto.supplier;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierDto implements Cloneable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String webSite;
    private String siret;
    private Date creationDate;
    private Date validationDate;

    public SupplierDto clone() throws CloneNotSupportedException {
        return (SupplierDto) super.clone();
    }
}
