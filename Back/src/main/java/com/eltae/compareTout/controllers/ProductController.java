package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.criteria.CriteriaFilterDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.ProductService;
import com.eltae.compareTout.services.SupplierService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Routes.PRODUCT)
@Api(value = "Products", description = "Products gesture", tags = {"Products"})
public class ProductController extends ExceptionCatcher {

    private ProductService productService;
    private SupplierService supplierService;


    @Autowired
    public ProductController(ProductService productService,SupplierService supplierService) {

        this.productService = productService;
        this.supplierService=supplierService;

    }

    @ApiOperation(value = "Provide a list of products from a category. If the list of criteria is provided " +
            "it will only retrieve the products that matches with the criteria and values off the list.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Query is successfully treated with both parameters"),
            @ApiResponse(code = 201, message = "Query is successfully treated categoryId parameter"),
            @ApiResponse(code = 404, message = "CategoryId or criteria are not present in database") })
    @PostMapping(produces="application/json",value = "/{categoryId}")
    public ResponseEntity<List<ProductDtoForFront>> getAllProductByCategoryAndCriteria(
            @ApiParam(value = "The identification number of the category. Must not be null",required = true)
            @PathVariable( value="categoryId") Long categoryId,
            @ApiParam(name = "criteriaFilters", value = "The list of criteria to filter products. " +
                    "fill the example to test, if you want more than one filter, just add another object" +
                    "in the list. ",defaultValue ="empty")@RequestBody(required = false) List<CriteriaFilterDto> criteriaFilterDtos) {
        if(criteriaFilterDtos == null)
            return ResponseEntity.status(201).body(this.productService.getAllProductsByCategory(categoryId));
        return ResponseEntity.status(200).body(this.productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos));
    }

    @ApiOperation(value = "Provide a json of all the criteria for a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Query is successfully treated "),
            @ApiResponse(code = 404, message = "The identification number of the product is unknown") })
    @GetMapping(produces="application/json",value = "/{idProduct}")
    public ResponseEntity<?> getAllCriteriaByProduct(
            @ApiParam(value = "The identification number of the product. Must not be null",required = true)
            @PathVariable long idProduct) {
        return ResponseEntity.status(200).body(this.productService.getAllCriteriaByProduct(idProduct));
    }

    @ApiOperation(value = "Add products with a CSV file (delimiter: ';')" +
            "Example of a ligne :" +
            "product's name ; product's description ;product supplier link's ;product's category_id;" +
            "products's criteria_id_1; product's value_1;products's criteria_id_2; product's value_2; ...")
    @PostMapping(consumes = "multipart/form-data",produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File is successful imported and treated. You can find in json response" +
                    " number lines added, the lines not added, and the error lines"),
            @ApiResponse(code = 400, message = "Wrong file format"),
            @ApiResponse(code = 500, message = "Unknown supplier identification number")  })
    public ResponseEntity<?> insertProductsFromFile(@ApiParam(value = "Your CSV file with the products to import. Cannot be empty. ",
            required = true)@RequestParam("file") MultipartFile file,
             @ApiParam(value="Your supplier identification number")@RequestParam("supplierId") Long id) {
        if(!file.getOriginalFilename().endsWith("csv")) {
            throw new ApplicationException(HttpStatus.resolve(400), "Wrong file format");
        }
        Supplier supplier =supplierService.getEntitySupplier(id);

        //if(supplier.getValidationDate()!=null) { // attendre la validation par l'admin
            String response = this.productService.insertProductsFromFile(file,supplier).toJSONString();
            return ResponseEntity.status(200).body(response);
        //}
        //else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier account don't have credentials");
    }

    @ApiOperation(value = "Produce a json with all supplier products.")
    @GetMapping(value = "/Supplier",produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 500, message = "Invalid supplier identification number.")})
    public ResponseEntity<?> getSupplierProducts(
            @ApiParam(value = "Identification number of the supplier",required = true)
            @RequestParam long id_supplier)
    {
        if(!this.supplierService.getSupplierWithId(id_supplier))
            return ResponseEntity.ok().body(this.productService.getSupplierProducts(id_supplier));
        else
            return ResponseEntity.ok().body("");

    }

}
