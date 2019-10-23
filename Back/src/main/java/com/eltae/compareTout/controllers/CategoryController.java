package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.CategoryService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;


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

    @ApiOperation(value = "Exporter le fichier des catégories")
    @GetMapping(value="/export-categories",produces="text/csv")
    public ResponseEntity generateReport() {
        try {
            File file = this.categoryService.getCategories();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + "Categorie_file" + ".csv")
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new FileSystemResource(file));

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate report: " + "Categorie_file", ex);
        }
    }


    }
