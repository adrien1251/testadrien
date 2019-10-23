package com.eltae.compareTout.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CriteriaProductPK implements Serializable {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Criteria criteria;

    public CriteriaProductPK clone() throws CloneNotSupportedException {
        return (CriteriaProductPK) super.clone();
    }
}