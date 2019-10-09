package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.CATEGORY, uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="child_id")
    private List<Category> childList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categoryList")
    private List<Criteria> criteriaList;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> productList;

    public Category clone() throws CloneNotSupportedException {
        return (Category) super.clone();
    }
}
