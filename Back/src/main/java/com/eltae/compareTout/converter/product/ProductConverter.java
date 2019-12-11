package com.eltae.compareTout.converter.product;

import com.eltae.compareTout.converter.CriteriaProductConverter;
import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.product.ProductDto;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter extends GenericsConverter<Product, ProductDto> {
    private final CriteriaProductConverter criteriaProductConverter;

    @Autowired
    public ProductConverter(CriteriaProductConverter criteriaProductConverter) {
        this.criteriaProductConverter = criteriaProductConverter;
    }

    public  List<ProductDto> ListEntityToDto(List<Product> productList) {
        List<ProductDto> dtoList=new ArrayList<ProductDto>();
        for(Product p : productList)
            dtoList.add(this.entityToDto(p));
        return dtoList;
    }

    public  List<ShortProductDto> listEntityToShortDto(List<Product> productList) {
        List<ShortProductDto> dtoList=new ArrayList<ShortProductDto>();
        for(Product p : productList)
            dtoList.add(this.entityToShortDto(p));
        return dtoList;
    }

    @Override
    public ProductDto entityToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .imageLink(product.getImageLink())
                .criteriaProducts(criteriaProductConverter.entityListToDtoList(product.getCriteriaProducts()))
                .build();
    }


    public ShortProductDto entityToShortDto(Product product) {
        return ShortProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getId())
                .imageLink(product.getImageLink())
                .supplier(product.getSupplier())
                .criteriaProducts(criteriaProductConverter.entityListToDtoList(product.getCriteriaProducts()))
                .build();
    }

    @Override
    public Product dtoToEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .build();
    }
}
