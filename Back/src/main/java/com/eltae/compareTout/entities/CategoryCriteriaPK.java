package com.eltae.compareTout.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CategoryCriteriaPK implements Serializable {

    @ManyToOne
    private Category category;

    @ManyToOne
    private Criteria criteria_cat;

    public CategoryCriteriaPK clone() throws CloneNotSupportedException {
        return (CategoryCriteriaPK) super.clone();
    }
}