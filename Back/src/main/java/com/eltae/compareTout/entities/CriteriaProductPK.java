package com.eltae.compareTout.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaProductPK implements Serializable {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Criteria criteria;

    public CriteriaProductPK clone() throws CloneNotSupportedException {
        return (CriteriaProductPK) super.clone();
    }
}