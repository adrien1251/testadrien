package com.eltae.compareTout.controllers;

import com.eltae.compareTout.dto.CriteriaFilterDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTest {
    @Autowired
    ProductController productController;

    @MockBean
    ProductService productService;

    @Test
    public void testGetAllProductByCategoryAndCriteriaReturnAllListOfProductForCategorieWhenNoCriteriaSpecified(){
        //Entry
        Long categoryIdEntry = 100L;
        List<CriteriaFilterDto> criteriaFilterDtos = null;

        //Effective
        List<ProductDtoForFront> effectiveProductDtoForFronts = new ArrayList<>();

        //Mock
        Mockito.when(productService.getAllProductsByCategory(categoryIdEntry)).thenReturn(effectiveProductDtoForFronts);

        //Expected
        List<ProductDtoForFront> expectedListproductController = productController.getAllProductByCategoryAndCriteria(categoryIdEntry, criteriaFilterDtos).getBody();

        assertEquals(effectiveProductDtoForFronts, expectedListproductController);
        Mockito.verify(productService, Mockito.times(1)).getAllProductsByCategory(categoryIdEntry);
    }

    @Test
    public void testGetAllProductByCategoryAndCriteriaReturnAllListOfProductForCategorieAndFilterByCriteria() {
        //Entry
        Long categoryIdEntry = 100L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();

        //Effective
        List<ProductDtoForFront> effectiveProductDtoForFronts = new ArrayList<>();

        //Mock
        Mockito.when(productService.getAllProductByCategoryAndCriteria(categoryIdEntry, criteriaFilterDtos)).thenReturn(effectiveProductDtoForFronts);

        //Expected
        List<ProductDtoForFront> expectedListproductController = productController.getAllProductByCategoryAndCriteria(categoryIdEntry, criteriaFilterDtos).getBody();

        assertEquals(effectiveProductDtoForFronts, expectedListproductController);
        Mockito.verify(productService, Mockito.times(1)).getAllProductByCategoryAndCriteria(categoryIdEntry, criteriaFilterDtos);
    }

}
