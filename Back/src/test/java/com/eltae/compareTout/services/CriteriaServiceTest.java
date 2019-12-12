package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.repositories.CategoryCriteriaRepository;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CriteriaServiceTest {

    @Autowired
    private CriteriaService criteriaService;

    @SpyBean
    @Autowired
    CategoryService categoryService;

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



    @Test
    public void testCreateCriteria() throws IOException {

        String fileName1 = "final_categories.csv";
        File file1 = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
        InputStream inputStream1 = new FileInputStream(file1);
        MultipartFile multipartFile1 = new MockMultipartFile(fileName1, inputStream1);
        this.categoryService.create(multipartFile1);

        //Entry
        String fileName = "final_criteres.csv";
        File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_criteres.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(fileName, inputStream);

        CategoryCriteria categoryCriteriaReturnedByMock = CategoryCriteria.builder().build();
        Optional<Category> categoryReturnedByFind = Optional.of(Category.
                builder()
                .id(154L)
                .name("Téléphonie")
                .categoryCriteriaList(new ArrayList<>()).build());

        Mockito.when(categoryRepository.findById(154L)).thenReturn(categoryReturnedByFind);

        Mockito.when(criteriaRepositoryMocked.findByName(Mockito.anyString()))
                .thenReturn(Optional.of(Criteria.builder().build()));

        Mockito.when(categoryMocked.getCriteriaProductWithCriteriaName(Mockito.anyString()))
                .thenReturn(categoryCriteriaReturnedByMock);
        Mockito.when(categoryCriteriaRepositoryMocked.save(Mockito.any(CategoryCriteria.class))).thenReturn(categoryCriteriaReturnedByMock);

        JSONObject json = this.criteriaService.create(multipartFile);
        assertEquals(1, json.get("Lines_Added"));
    }

    @Test
    public void testCreateCriteriaWhenCritDoesntNotExist() throws IOException {

        String fileName1 = "final_categories.csv";
        File file1 = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
        InputStream inputStream1 = new FileInputStream(file1);
        MultipartFile multipartFile1 = new MockMultipartFile(fileName1, inputStream1);
        this.categoryService.create(multipartFile1);

        //Entry
        String fileName = "final_criteres.csv";
        File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_criteres.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(fileName, inputStream);

        CategoryCriteria categoryCriteriaReturnedByMock = CategoryCriteria.builder().build();
        Optional<Category> categoryReturnedByFind = Optional.of(Category.
                builder()
                .id(154L)
                .name("Téléphonie")
                .categoryCriteriaList(new ArrayList<>()).build());

        Mockito.when(categoryRepository.findById(154L)).thenReturn(categoryReturnedByFind);

        Mockito.when(criteriaRepositoryMocked.findByName(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(categoryMocked.getCriteriaProductWithCriteriaName(Mockito.anyString()))
                .thenReturn(categoryCriteriaReturnedByMock);
        Mockito.when(categoryCriteriaRepositoryMocked.save(Mockito.any(CategoryCriteria.class))).thenReturn(categoryCriteriaReturnedByMock);

        JSONObject json = this.criteriaService.create(multipartFile);
        assertEquals(1, json.get("Lines_Added"));
    }


    @Test
    public void testGetAllMandatoryCriteriasIdWithIdCategory(){

        Category categoryEntry = Category.builder()
                .id(154L)
                .name("Téléphonie")
                .categoryCriteriaList(new ArrayList<>())
                .build();

        Criteria c1 = Criteria.builder().id(5L).build();
        Criteria c2 = Criteria.builder().id(6L).build();
        Criteria c3 = Criteria.builder().id(7L).build();
        CategoryCriteriaPK pk1 = new CategoryCriteriaPK();
        pk1.setCriteria_cat(c1);
        CategoryCriteriaPK pk2 = new CategoryCriteriaPK();
        pk2.setCriteria_cat(c2);
        CategoryCriteriaPK pk3 = new CategoryCriteriaPK();
        pk3.setCriteria_cat(c3);
        List<CategoryCriteria> categoryCriteriaListReturnedByMock = new ArrayList<>();
        categoryCriteriaListReturnedByMock.add(CategoryCriteria.builder()
                .isMandatory(true)
                .pk(pk1)
                .build());
        categoryCriteriaListReturnedByMock.add(CategoryCriteria.builder()
                .isMandatory(false)
                .pk(pk2).build());
        categoryCriteriaListReturnedByMock.add(CategoryCriteria
                .builder()
                .isMandatory(true)
                .pk(pk3).build());

        Mockito.when(categoryCriteriaRepositoryMocked.findByPk_Category(categoryEntry)).thenReturn(categoryCriteriaListReturnedByMock);

        ArrayList<Long> resultFromCall = this.criteriaService.getAllMandatoryCriteriasIdWithIdCategory(categoryEntry);
        assertEquals(2,resultFromCall.size());
    }



}
