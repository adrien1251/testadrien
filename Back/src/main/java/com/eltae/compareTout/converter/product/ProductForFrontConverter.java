package com.eltae.compareTout.converter.product;

import com.eltae.compareTout.converter.CriteriaProductConverter;
import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductForFrontConverter extends GenericsConverter<Product, ProductDtoForFront> {

    private final CriteriaProductConverter criteriaProductConverter;

    @Autowired
    public ProductForFrontConverter(CriteriaProductConverter criteriaProductConverter) {
        this.criteriaProductConverter = criteriaProductConverter;
    }

    @Override
    public ProductDtoForFront entityToDto(Product product) {
        return ProductDtoForFront.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .supplierLink(product.getSupplierLink())
                .imageLinkLink(product.getImageLink())
                .criteriaDtoList(criteriaProductConverter.entityListToDtoList(product.getCriteriaProducts()))
                .build();
    }

    @Override
    public Product dtoToEntity(ProductDtoForFront productDtoForFront) {
        return null;
    }
}
