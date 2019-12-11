package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.dto.category.CategoryDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.CategoryCriteria;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @SpyBean
    @Autowired
    CategoryConverter categoryConverter;


    @Autowired
    private CategoryRepository categoryRepository;


        @Test
        public void testCreateCategory() throws IOException {

            //Entry
            String fileName = "final_categories.csv";
            File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(fileName,inputStream);

            JSONObject json = this.categoryService.create(multipartFile);
            assertEquals(1, json.get("Lines_Added"));
    }


    @Test
    public void testGetCategory() throws IOException {

        //Entry
        String fileName = "final_categories.csv";
        File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(fileName,inputStream);

        this.categoryService.create(multipartFile);
        File fileReturned = this.categoryService.getCategories();
        InputStream inputStreamReturned = new FileInputStream(fileReturned);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStreamReturned, StandardCharsets.UTF_8)).withCSVParser(parser).build();
        int nbLignes = 0;
        while ((csvReader.readNext()) != null)
            nbLignes ++;

            assertEquals(2, nbLignes);


    }



}
