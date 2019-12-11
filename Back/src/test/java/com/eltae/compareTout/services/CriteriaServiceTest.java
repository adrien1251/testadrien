package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.CategoryCriteria;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.repositories.CategoryCriteriaRepository;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CriteriaServiceTest {

    @Autowired
    CriteriaService criteriaService;

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

        @Test
        public void testCreateCriteria() throws IOException {

            String fileName1 = "final_categories.csv";
            File file1 = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
            InputStream inputStream1 = new FileInputStream(file1);
            MultipartFile multipartFile1 = new MockMultipartFile(fileName1,inputStream1);
            this.categoryService.create(multipartFile1);

            //Entry
            String fileName = "final_criteres.csv";
            File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_criteres.csv").getFile());
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(fileName,inputStream);

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
        MultipartFile multipartFile1 = new MockMultipartFile(fileName1,inputStream1);
        this.categoryService.create(multipartFile1);

        //Entry
        String fileName = "final_criteres.csv";
        File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_criteres.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(fileName,inputStream);

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




}
