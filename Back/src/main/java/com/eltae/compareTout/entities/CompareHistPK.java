package com.eltae.compareTout.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompareHistPK implements Serializable {

    @Column(name = "PRODUCT_ID")
    private Long product_id;

    @Column(name = "CUSTOMER_ID")
    private Long customer_id;


    public CompareHistPK clone() throws CloneNotSupportedException {
        return (CompareHistPK) super.clone();
    }
}