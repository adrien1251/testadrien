package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = Tables.CRITERIA_PRODUCT)
@AssociationOverrides({
    @AssociationOverride(name = "pk.criteria", joinColumns = @JoinColumn(name = "criteria_id")),
    @AssociationOverride(name = "pk.product", joinColumns = @JoinColumn(name = "product_id"))
})
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaProduct implements Cloneable {

    @EmbeddedId
    private CriteriaProductPK pk = new CriteriaProductPK();

    private String value;

    @Transient
    public Criteria getCriteria() {
        return getPk().getCriteria();
    }

    @Transient
    public Product getProduct() {
        return getPk().getProduct();
    }

    public CriteriaProduct clone() throws CloneNotSupportedException {
        return (CriteriaProduct) super.clone();
    }

}
