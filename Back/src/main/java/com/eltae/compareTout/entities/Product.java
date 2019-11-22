package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name="product_generator", sequenceName = "product_seq")
    @Column(name = "product_id")
    private Long id;

    private String name;

    @Column(length = 1500)
    private String description;

    @Column(length = 750)
    private String supplierLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product")
    private List<CriteriaProduct> criteriaProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    public Product(Product p){
        this.id = p.id;
        this.category = p.category;
        this.criteriaProducts = p.criteriaProducts;
        this.description = p.description;
        this.name = p.name;
        this.supplierLink = p.supplierLink;
        this.supplier=p.supplier;
    }

    public List<Long> getCrit() {
        List<Long> crit = new ArrayList<>();
        for (CriteriaProduct c : this.criteriaProducts) {
            crit.add(c.getCriteria().getId());
        }
            return crit;
        }


    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

    public void addCriteriaProduct(CriteriaProduct criteria){
        List<CriteriaProduct> list = this.getCriteriaProducts();
        list.add(criteria);
        this.setCriteriaProducts(list);
    }

}
