package com.eltae.compareTout.dto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.Product;
import lombok.*;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShortCategoryDto implements Cloneable{
    private Long id;
    private String name;
    private List<Criteria> criteriaList;
    private List<Product> productList;


    public ShortCategoryDto clone() throws CloneNotSupportedException {
        return (ShortCategoryDto) super.clone();
    }
}
