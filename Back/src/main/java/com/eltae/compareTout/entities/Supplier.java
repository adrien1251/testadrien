package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.SUPPLIER)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends User implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String webSite;

    private String siret;

    public Supplier clone() throws CloneNotSupportedException {
        return (Supplier) super.clone();
    }
}
