package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.CategoryService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping(Routes.CATEGORY)
public class CategoryController extends ExceptionCatcher {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Ajout de catégorie par fichier csv (delimiter: `;`)")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Integer> createUser(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(201).body(this.categoryService.create(file));
    }
}
