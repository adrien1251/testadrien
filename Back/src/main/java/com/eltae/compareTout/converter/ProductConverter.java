package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter extends GenericsConverter<Product, ProductDto> {

    @Autowired
    private ProductRepository productRepository;

    public  List<ProductDto> ListEntityToDto(List<Product> productList) {
        List<ProductDto> dtoList=new ArrayList<ProductDto>();
        for(Product p : productList)
            dtoList.add(this.entityToDto(p));
        return dtoList;
    }


    public  List<ShortProductDto> ListEntityToShortDto(List<Product> productList) {
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
                .criteriaProducts(getCriteria(product.getCriteriaProducts()))
                .build();
    }


    public ShortProductDto entityToShortDto(Product product) {
        return ShortProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getId())
                .criteriaProducts(getCriteria(product.getCriteriaProducts()))
                .build();
    }


    private List<CriteriaProductDto> getCriteria(List<CriteriaProduct> criteriaProducts) {
        List<CriteriaProductDto> critereIds = new ArrayList<>();
        for(CriteriaProduct criteriaProduct: criteriaProducts) {
            critereIds.add(CriteriaProductDto.builder()
                    .id(criteriaProduct.getCriteria().getId())
                    .value(criteriaProduct.getValue())
                    .criteriaName(criteriaProduct.getCriteria().getName())
                    .criteriaUnit(criteriaProduct.getCriteria().getUnit())
                 //   .isMandatory(criteriaProduct.getCriteria().isMandatory())
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
