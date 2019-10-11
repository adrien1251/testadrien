package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.COMPAREHIST)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompareHist implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Customer idCustomer;


    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> productsToCompare;

    public CompareHist clone() throws CloneNotSupportedException {
        return (CompareHist) super.clone();
    }

}