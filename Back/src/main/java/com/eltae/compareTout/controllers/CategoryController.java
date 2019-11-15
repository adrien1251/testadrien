package com.eltae.compareTout.controllers;
import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortCategoryDto;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.CategoryService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(Routes.CATEGORY)
@Api(value = "Categories", description = "Categories gesture", tags = {"Categories"})
public class CategoryController extends ExceptionCatcher {

    private CategoryService categoryService;



    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Add categories with a CSV file (delimiter: `;`)   " +
                    "  Exemple of a line :   "+
                    "  Main_category ; First_child_category ;... ; Last_child_category")
    @PostMapping(consumes = "multipart/form-data",produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File is successful imported and treated. You can find in json response" +
                    " number lines added, the lines not added, and the error lines"),
            @ApiResponse(code = 400, message = "Wrong file format") })
    public ResponseEntity<?> addCategories(@ApiParam(value = "Your CSV file with the categories to import. Cannot be empty. ",required = true)@RequestParam("file") MultipartFile file) {
        if(!file.getOriginalFilename().endsWith("csv")) {
            throw new ApplicationException(HttpStatus.resolve(400), "Wrong file format");
        }
        else
            return ResponseEntity.ok(this.categoryService.create(file).toJSONString());
    }

    @ApiOperation(value = "Export a CSV file with a list of the categories available for products")
    @GetMapping(value="/export",produces="text/csv")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File is successful created and ready to export"),
            @ApiResponse(code = 500, message = "Wrong file format") })
    public ResponseEntity generateReport() {
        try {
            File file = this.categoryService.getCategories();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + "Categorie_file" + ".csv")
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new FileSystemResource(file));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate categories CSV file", ex);
        }
    }

    @ApiOperation(value = "Produces json with main categories if no query parameters. If an identification number " +
            "of a category is provided, it returns its first level subclasses")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 500, message = "No value present")})
    protected ResponseEntity<?> getMainCategories(@ApiParam(value = "Category identification number") @RequestParam(value="id",required = false)Long id) {
            if(id==null) {
            Gson gson = new Gson();
            String jsonList = gson.toJson(this.categoryService.getMainCategories());
            return ResponseEntity.ok().body(jsonList);
        }
        else{
            if(this.categoryService.getChildCategories(id)==null)
                return (ResponseEntity<?>) ResponseEntity.notFound();
            else
                return ResponseEntity.ok().body(this.categoryService.getChildCategories(id));
        }
    }

    /*
    @ApiOperation(value = "Liste des critères d'une catégorie ")
    @GetMapping(value="/category-criteria/{id}")
    public ResponseEntity<List<CriteriaProductDto>> getCriteriaCategory(@PathVariable Long id) {
        return ResponseEntity.status(201).body(this.categoryService.getCriteriaCategories(id));
    }
    @ApiOperation(value = "Liste des produits d'une catégorie ")
    @GetMapping(value="/category-products/{id}")
    public ResponseEntity<List<ShortProductDto>> getProductsCategory(@PathVariable Long id) {
        return ResponseEntity.status(201).body(this.categoryService.getProductsCategory(id));
    }
    */
    }
