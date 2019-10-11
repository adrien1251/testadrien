package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.SUPPLIER)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="id")
    private User idProduct;

    private String webSite;

    private String siret;

    public Supplier clone() throws CloneNotSupportedException {
        return (Supplier) super.clone();
    }
}
