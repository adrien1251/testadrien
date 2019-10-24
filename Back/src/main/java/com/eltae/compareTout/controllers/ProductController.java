package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.ProductDto;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.CategoryService;
import com.eltae.compareTout.services.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Routes.PRODUCT)
public class ProductController extends ExceptionCatcher {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<List<ProductDto>> getAllProductByCategory(@PathVariable long idCategory) {
        return ResponseEntity.status(201).body(this.productService.getAllProductsByCategory(idCategory));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        return ResponseEntity.status(201).body(this.productService.getAll());
    }




}
