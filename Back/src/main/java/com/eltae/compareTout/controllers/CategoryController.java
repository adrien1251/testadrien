package com.eltae.compareTout.controllers;
import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.CategoryService;
import io.swagger.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;

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
            "  Exemple of a line :   " +
            "  Main_category ; First_child_category ;... ; Last_child_category")
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File is successful imported and treated. You can find in json response" +
                    " number lines added, the lines not added, and the error lines"),
            @ApiResponse(code = 400, message = "Wrong file format")})
    public ResponseEntity<?> addCategories(@ApiParam(value = "Your CSV file with the categories to import. Cannot be empty. ", required = true) @RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith("csv")) {
            throw new ApplicationException(HttpStatus.resolve(400), "Wrong file format");
        } else
            return ResponseEntity.ok(this.categoryService.create(file).toJSONString());
    }


    @ApiOperation(value = "Produces json with main categories if no query parameters and media type json. If an identification number " +
            "of a category is provided and media type json , it returns its first level subclasses; If media type text/csv is provided" +
            "in header without query parameters, it will export a CSV file with a list of the categories available for products")
    @GetMapping(produces = {"application/json", "text/csv"},value={"/_search"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 406, message = "You can't export filtered category only in json format"),
            @ApiResponse(code = 415, message = "Empty media type not supported"),
            @ApiResponse(code = 500, message = "Unable to generate report")
    })
    protected ResponseEntity<?> getMainCategories(
            @ApiParam(value = "Category identification number")
            @RequestParam(value = "category_id", required = false) Long category_id,
            @RequestHeader HttpHeaders httpHeaders) {
        System.out.println(httpHeaders);
        if (httpHeaders.getAccept().isEmpty() )
            throw new ApplicationException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Please check your media type");
        if (httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON)) {
            if (category_id == null)
                return ResponseEntity.ok().body(this.categoryService.getMainCategories());
            else {
                if (this.categoryService.getCategoryWithId(category_id) == null)
                    throw new ApplicationException(HttpStatus.NOT_FOUND, "Category id not present in database");
                else
                    return ResponseEntity.ok().body(this.categoryService.getChildCategories(category_id));
            }
        }
        if (!httpHeaders.getAccept().contains(MediaType.parseMediaType("text/csv")))
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Only media type json for filtered category");
        else{
            if (category_id != null)
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "Please check your request");
            else {
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
        }
        }
    }




