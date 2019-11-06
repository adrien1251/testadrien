package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.CATEGORY_CRITERIA)
@AssociationOverrides({
    @AssociationOverride(name = "pk.criteria_cat", joinColumns = @JoinColumn(name = "criteria_id")),
    @AssociationOverride(name = "pk.category", joinColumns = @JoinColumn(name = "category_id"))
})
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCriteria implements Cloneable {

    @EmbeddedId
    private CategoryCriteriaPK pk = new CategoryCriteriaPK();

    private Boolean isMandatory;

    @Transient
    public Criteria getCriteria() {
        return getPk().getCriteria_cat();
    }

    @Transient
    public Category getCategory() {
        return getPk().getCategory();
    }

    public CategoryCriteria clone() throws CloneNotSupportedException {
        return (CategoryCriteria) super.clone();
    }

}
