package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Tables.PRODUCT)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String supplierLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    /*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productList")
    private List<CriteriaProduct> criteriaList;

     */

    @OneToMany(mappedBy = "criteria")
    private Set<CriteriaProduct> employerDeliveryAgent = new HashSet<CriteriaProduct>();

    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }
}
