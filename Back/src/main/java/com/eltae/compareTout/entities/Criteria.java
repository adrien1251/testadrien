package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.CRITERIA)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Criteria implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String type;

    private String value;

    private String unit;

    private boolean isMandatory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criteria_category",
            joinColumns = { @JoinColumn(name = "fk_criteria") },
            inverseJoinColumns = { @JoinColumn(name = "fk_category") })
    private List<Category> categoryList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criteria_product",
            joinColumns = { @JoinColumn(name = "fk_criteria") },
            inverseJoinColumns = { @JoinColumn(name = "fk_product") })
    private List<Product> productList;

    public Criteria clone() throws CloneNotSupportedException {
        return (Criteria) super.clone();
    }
}
