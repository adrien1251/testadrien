package com.eltae.compareTout.repositories.product;

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
    Long categoryId;

    @NotNull
    List<Long> criteriaIds;
}
