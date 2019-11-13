package com.eltae.compareTout.repositories.product;

import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.OurCriteriaBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Data
public class ProductCriteria {
    @NotNull
    Category category;

    @NotNull
    List<OurCriteriaBuilder> criteriaBuilders;
}
