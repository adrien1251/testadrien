package com.eltae.compareTout.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CriteriaProductPK implements Serializable {

    @Column(name = "PRODUCT_ID")
    private Long product_id;

    @Column(name = "CRITERIA_ID")
    private Long criteria_id;
}