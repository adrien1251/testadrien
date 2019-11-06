package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Tables.CATEGORY, uniqueConstraints = @UniqueConstraint(columnNames = "name", name="category_unique_name"))
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name="category_generator", sequenceName = "category_seq")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Category> childList;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categoryList")
    private List<Criteria> criteriaList;


    public Category clone() throws CloneNotSupportedException {
        return (Category) super.clone();
    }

    @Override
    public String toString(){
        return id + ": " + name;
    }


}
