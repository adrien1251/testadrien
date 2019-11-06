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

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "pk.criteria_cat", cascade=CascadeType.ALL)
    private List<CategoryCriteria> categoryList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.criteria", cascade=CascadeType.ALL)
    private List<CriteriaProduct> criteriaProducts = new ArrayList<>();

    public Criteria clone() throws CloneNotSupportedException {
        return (Criteria) super.clone();
    }


}
