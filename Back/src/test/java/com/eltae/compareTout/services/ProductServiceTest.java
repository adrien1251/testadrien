package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.dto.criteria.CriteriaFilterDto;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ProductDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductConverter productConverter;

    @MockBean
    CriteriaRepository criteriaRepository;

    @Test
    public void testGetAllProductsByCategoryReturnAllProductWhenGreatCategoryParam(){
        //Entry
        Long categoryId = 1L;

        //Effective
        List<Product> productReturnByRepo = new ArrayList<>();
        List<ProductDtoForFront> effectiveProductDtoForFronts= new ArrayList<>();

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.builder().build()));
        Mockito.when(productRepository.findAllByCategoryId(categoryId)).thenReturn(productReturnByRepo);

        //Expected
        List<ProductDtoForFront> expectedProductDtoForFront = productService.getAllProductsByCategory(categoryId);

        assertEquals(effectiveProductDtoForFronts, expectedProductDtoForFront);
    }

    @Test(expected = ApplicationException.class)
    public void testGetAllProductsByCategoryReturnApplicationExceptionWhenCategoryNotExist() {
            //Entry
            Long categoryId = 1L;

            //Effective

            //Mock
            Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

            List<ProductDtoForFront> expectedProductDtoForFront = productService.getAllProductsByCategory(categoryId);
    }

    @Test(expected = ApplicationException.class)
    public void testGetAllProductByCategoryAndCriteriaReturnExceptionIfCategoryNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        //Effective

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos);
    }


    @Test(expected = ApplicationException.class)
    public void testGetAllProductByCategoryAndCriteriaReturnExceptionIfCriteriaNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        criteriaFilterDtos.add(CriteriaFilterDto.builder()
                .idCriteria(1L)
                .build());
        //Effective

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.builder().build()));
        Mockito.when(criteriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos);
    }



    @Test(expected = ApplicationException.class)
    public void testGetAllCriteriaByProductReturnExceptionIfProductNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        criteriaFilterDtos.add(CriteriaFilterDto.builder()
                .idCriteria(1L)
                .build());
        //Effective

        //Mock

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(null);

        productService.getAllCriteriaByProduct(categoryId);
    }


}
