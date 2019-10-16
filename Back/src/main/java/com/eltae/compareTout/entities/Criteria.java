package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String unit;

    private boolean isMandatory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criteria_category",
            joinColumns = { @JoinColumn(name = "fk_criteria") },
            inverseJoinColumns = { @JoinColumn(name = "fk_category") })
    private List<Category> categoryList;

    @OneToMany(mappedBy = "product")
    private Set<CriteriaProduct> employerDeliveryAgent = new HashSet<CriteriaProduct>();


    public Criteria clone() throws CloneNotSupportedException {
        return (Criteria) super.clone();
    }
}
