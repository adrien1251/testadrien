package com.eltae.compareTout.dto.category;

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
public class CategoryDto implements Cloneable{
    private Long id;
    private String name;
    private List<Category> childList;
    private Category parent;
    private List<Criteria> criteriaList;
    private List<Product> productList;



    public CategoryDto clone() throws CloneNotSupportedException {
        return (CategoryDto) super.clone();
    }
}
