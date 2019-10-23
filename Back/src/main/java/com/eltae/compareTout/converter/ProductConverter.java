package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ProductDto;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter extends GenericsConverter<Product, ProductDto> {

    @Override
    public ProductDto entityToDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .criteriaProducts(getCriteria(product.getCriteriaProducts()))
                .build();
    }

    private List<CriteriaProductDto> getCriteria(List<CriteriaProduct> criteriaProducts) {
        List<CriteriaProductDto> critereIds = new ArrayList<>();
        for(CriteriaProduct criteriaProduct: criteriaProducts) {
            critereIds.add(CriteriaProductDto.builder()
                    .value(criteriaProduct.getValue())
                    .criteriaName(criteriaProduct.getCriteria().getName())
                    .criteriaUnit(criteriaProduct.getCriteria().getUnit())
                    .isMandatory(criteriaProduct.getCriteria().isMandatory())
                    .type(criteriaProduct.getCriteria().getType())
                    .build());
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
