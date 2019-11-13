package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.CriteriaFilterDto;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Liste des produits d'une catégorie potentiellement filtré par critère")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDtoForFront>> getAllProductByCategoryAndCriteria(
            @PathVariable Long categoryId,
            @ApiParam(name = "criteriaFilters", value = "Criteria list") @RequestBody(required = false) List<CriteriaFilterDto> criteriaFilterDtos) {

        if(criteriaFilterDtos == null)
            return ResponseEntity.status(201).body(this.productService.getAllProductsByCategory(categoryId));
        return ResponseEntity.status(200).body(this.productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos));
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
