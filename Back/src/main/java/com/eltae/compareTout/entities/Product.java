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
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String description;

    private String supplierLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product")
    private List<CriteriaProduct> criteriaProducts;

    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }
}
