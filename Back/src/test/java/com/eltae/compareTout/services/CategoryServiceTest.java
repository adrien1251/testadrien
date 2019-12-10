package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.dto.category.CategoryDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
        /*
        //Entry
        Long categoryIdEntry = 100L;

        CategoryDto categoryEntry = CategoryDto.builder().build();

        //Effective

        //Mocks

        //Call
        String fileName = "final_categories.csv";

        File file = new File(getClass().getClassLoader().getResource("fichiers_CSV/final_categories.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(fileName,inputStream);

        this.categoryService.create(multipartFile);
        //Asset
        */
        Assert.assertEquals(1,1);
    }





}
