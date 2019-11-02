package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    public List<ShortProductDto> getAll() {
        return productConverter.ListEntityToShortDto(this.productRepository.findAll());
    }

    public List<ShortProductDto> getAllProductsByCategory(long idCategory) {
        return productConverter.ListEntityToShortDto(this.productRepository.findAllByCategoryId(idCategory));
    }

    public List<CriteriaProductDto> getAllCriteriaByProduct(long idProduct) {
        return productConverter.entityListToDtoList(this.productRepository.findProductById(idProduct)).get(0).getCriteriaProducts();
    }
}