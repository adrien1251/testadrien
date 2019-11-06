package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Routes.PRODUCT)
@Api(value = "Products", description = "Products gesture", tags = {"Products"})
public class ProductController extends ExceptionCatcher {

    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Liste des produits d'une catégorie filtré par critère")
    @GetMapping("/categories/{idCategory}")
    public ResponseEntity<List<ShortProductDto>> getAllProductByCategoryAndCriteria(
            @PathVariable long idCategory,
            @RequestBody List<Long> idCriteria) {
        return ResponseEntity.status(200).body(this.productService.getAllProductByCategoryAndCriteria(idCategory, idCriteria));
    }


    @ApiOperation(value = "Liste des produits d'une catégorie ")
    @GetMapping("/category/{idCategory}")
    public ResponseEntity<List<ShortProductDto>> getAllProductByCategory(@PathVariable long idCategory) {
        return ResponseEntity.status(201).body(this.productService.getAllProductsByCategory(idCategory));
    }

    @ApiOperation(value = "Liste des critères d'un produit ")
    @GetMapping("/criteria/{idProduct}")
    public ResponseEntity<List<CriteriaProductDto>> getAllCriteriaByProduct(@PathVariable long idProduct) {
        return ResponseEntity.status(201).body(this.productService.getAllCriteriaByProduct(idProduct));
    }
    @GetMapping
    public ResponseEntity<List<ShortProductDto>> getAllProduct() {
        return ResponseEntity.status(201).body(this.productService.getAll());
    }

    @ApiOperation(value = "Ajout de produits par fichier csv (delimiter: ';')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Integer> insertProductsFromFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(201).body(this.productService.insertProductsFromFile(file));
    }



}
