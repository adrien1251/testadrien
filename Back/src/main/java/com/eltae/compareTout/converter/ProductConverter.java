package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.ProductDto;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ProductConverter extends GenericsConverter<Product, ProductDto> {
    @Override
    public ProductDto entityToDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .criteriaIds(getCriteriaId(product.getCriteriaList()))
                .build();
    }

    private List<Long> getCriteriaId(List<Criteria> criteriaList) {
        List<Long> critereIds = new ArrayList<>();
        for(Criteria criteria: criteriaList) {
            critereIds.add(criteria.getId());
        }
        return critereIds;
    }

    @Override
    public Product dtoToEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .build();
    }
}
