package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.repositories.CategoryCriteriaRepository;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CriteriaServiceTests {

    @Autowired
    private CriteriaService criteriaService;


    @SpyBean
    @Autowired
    CriteriaConverter criteriaConverter;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @MockBean
    private CriteriaRepository criteriaRepositoryMocked;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private Category categoryMocked;

    @MockBean
    private CategoryCriteriaRepository categoryCriteriaRepositoryMocked;

    @MockBean
    private CriteriaProductRepository criteriaProductRepositoryMocked;


    @MockBean
    private CategoryService categoryServiceMocked;


    @Test
    public void testGetAllcriteria(){

        List<Criteria> criteriaListReturnedByRepo = new ArrayList<>();
        criteriaListReturnedByRepo.add(Criteria.builder().id(1L).build());
        List<CriteriaProductDto> listReturnByConv = this.criteriaConverter.entityListToDtoList(criteriaListReturnedByRepo);

        Mockito.when(this.criteriaRepository.findAll()).thenReturn(criteriaListReturnedByRepo);
        Mockito.when(this.criteriaConverter.entityListToDtoList(criteriaListReturnedByRepo)).thenReturn(listReturnByConv);
        List<CriteriaProductDto> listResult = this.criteriaService.getAllcriteria();

        assertEquals(1, listResult.size());

    }

    @Test
    public void testGetAllCriteriaAndAllValuesAssociatesToACategoryWithNumeric(){

        Criteria criteria = Criteria.builder().id(100L).build();

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(criteria);

        CriteriaProductPK pk1 = new CriteriaProductPK();
        pk1.setCriteria(criteria);

        Criteria c1 = Criteria.builder().id(5L).build();
        CategoryCriteriaPK categoryCriteriaPK1 = new CategoryCriteriaPK();
        categoryCriteriaPK1.setCriteria_cat(c1);
        CategoryCriteria categoryCriteria = CategoryCriteria.builder().pk(categoryCriteriaPK1).build();
        List<CategoryCriteria> categoryCriteriaList = new ArrayList<>();
        categoryCriteriaList.add(categoryCriteria);

        Category categoryReturnedByCategoryServ = Category.builder().id(5L).name("Ma categorie").categoryCriteriaList(categoryCriteriaList).build();

        List<CriteriaProduct> criteriaProductListReturnedByRepo = new ArrayList<>();
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("10.0")
                .pk(pk1).build());
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("600.0")
                .pk(pk1).build());
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("300.0")
                .pk(pk1).build());


//        CategoryService categoryServiceMocked = Mockito.mock(CategoryService.class);

        Mockito.when(this.categoryServiceMocked.getCategoryWithId(154L)).thenReturn(categoryReturnedByCategoryServ);
        Mockito.when(this.criteriaProductRepositoryMocked.findDistinctByPk_CriteriaAndAndPk_ProductCategory(c1, categoryReturnedByCategoryServ))
                .thenReturn(criteriaProductListReturnedByRepo);

        Map<Long, List<String>> mapResult = this.criteriaService.getAllCriteriaAndAllValuesAssociatesToACategory(154L);
        List<String> valeurTrie = mapResult.get(100L);

        assertEquals(1,mapResult.size());
        assertEquals("10.0",valeurTrie.get(0));
        assertEquals("300.0",valeurTrie.get(1));
        assertEquals("600.0",valeurTrie.get(2));

    }



    @Test
    public void testGetAllCriteriaAndAllValuesAssociatesToACategoryWithLetters(){

        Criteria criteria = Criteria.builder().id(100L).build();

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(criteria);

        CriteriaProductPK pk1 = new CriteriaProductPK();
        pk1.setCriteria(criteria);

        Criteria c1 = Criteria.builder().id(5L).build();
        CategoryCriteriaPK categoryCriteriaPK1 = new CategoryCriteriaPK();
        categoryCriteriaPK1.setCriteria_cat(c1);
        CategoryCriteria categoryCriteria = CategoryCriteria.builder().pk(categoryCriteriaPK1).build();
        List<CategoryCriteria> categoryCriteriaList = new ArrayList<>();
        categoryCriteriaList.add(categoryCriteria);

        Category categoryReturnedByCategoryServ = Category.builder().id(5L).name("Ma categorie").categoryCriteriaList(categoryCriteriaList).build();

        List<CriteriaProduct> criteriaProductListReturnedByRepo = new ArrayList<>();
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("H")
                .pk(pk1).build());
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("O")
                .pk(pk1).build());
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("A")
                .pk(pk1).build());
        criteriaProductListReturnedByRepo.add(CriteriaProduct.builder()
                .value("Z")
                .pk(pk1).build());


//        CategoryService categoryServiceMocked = Mockito.mock(CategoryService.class);

        Mockito.when(this.categoryServiceMocked.getCategoryWithId(154L)).thenReturn(categoryReturnedByCategoryServ);
        Mockito.when(this.criteriaProductRepositoryMocked.findDistinctByPk_CriteriaAndAndPk_ProductCategory(c1, categoryReturnedByCategoryServ))
                .thenReturn(criteriaProductListReturnedByRepo);

        Map<Long, List<String>> mapResult = this.criteriaService.getAllCriteriaAndAllValuesAssociatesToACategory(154L);
        List<String> valeurTrie = mapResult.get(100L);

        assertEquals(1,mapResult.size());
        assertEquals("a",valeurTrie.get(0));
        assertEquals("h",valeurTrie.get(1));
        assertEquals("o",valeurTrie.get(2));
        assertEquals("z",valeurTrie.get(3));

    }


    @Test
    public void testGetCriteriaProductWithIdCriteria() throws CloneNotSupportedException {

        Criteria criteriaEntry = Criteria.builder().id(1L).name("MyCrit").build();

        Criteria criteriaReturnByRepo = criteriaEntry.clone();
        Mockito.when(this.criteriaRepository.findById(1L)).thenReturn(Optional.of(criteriaReturnByRepo));

        Criteria criteriaReturn = this.criteriaService.getCriteriaProductWithIdCriteria(1L);
        assertEquals(criteriaEntry,criteriaReturn);
    }

    @Test
    public void testGetCriteriaProductWithIdCriteriaWhenCriteriaDoesntNotExit() throws CloneNotSupportedException {

        Mockito.when(this.criteriaRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(this.criteriaService.getCriteriaProductWithIdCriteria(1L));
    }

}
