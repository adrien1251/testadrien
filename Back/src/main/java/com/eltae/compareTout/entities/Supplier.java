package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SuperBuilder
@DiscriminatorValue("SUPPLIER")
@Table(name = Tables.SUPPLIER)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends User implements Cloneable{

    private String webSite;
    private String siret;
    private LocalDate validationDate;

    public Supplier clone() throws CloneNotSupportedException {
        return (Supplier) super.clone();
    }
}
