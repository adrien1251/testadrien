package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Tables.CRITERIA, uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Criteria implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_generator")
    @SequenceGenerator(name="criteria_generator", sequenceName = "criteria_seq")
    @Column(name = "criteria_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private TypeCriteria type;

    private String unit;

    private boolean isMandatory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criteria_category",
            joinColumns = { @JoinColumn(name = "fk_criteria") },
            inverseJoinColumns = { @JoinColumn(name = "fk_category") })
    private List<Category> categoryList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.criteria", cascade=CascadeType.ALL)
    private List<CriteriaProduct> criteriaProducts = new ArrayList<>();

    public Criteria clone() throws CloneNotSupportedException {
        return (Criteria) super.clone();
    }

    public void addCategory(Category category){
        List<Category> list = this.getCategoryList();
        list.add(category);
        this.setCategoryList(list);
    }
}
