package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.CRITERIA_PRODUCT)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaProduct implements Cloneable {

    @EmbeddedId
    private CriteriaProductPK id;

    @ManyToOne
    @MapsId("Criteria_id")
    @JoinColumn(name = "CRITERIA_ID")
    private Criteria criteria;

    @ManyToOne
    @MapsId("Product_id")
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private String value;

    public CriteriaProduct clone() throws CloneNotSupportedException {
        return (CriteriaProduct) super.clone();
    }

}
