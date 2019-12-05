package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.CategoryService;
import com.eltae.compareTout.services.CriteriaService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Routes.CRITERIA)
@Api(value = "Criteria", description = "Criteria gesture", tags = {"Criteria"})
public class CriteriaController   extends ExceptionCatcher {

    private CriteriaService criteriaService;
    private CategoryService categoryService;


    public CriteriaController(CriteriaService criteriaService,CategoryService catService) {
        this.criteriaService = criteriaService;
        this.categoryService = catService;
    }

    @ApiOperation(value = "Produces json with all criteria available if no query parameters. If an identification number " +
            "of a category is provided, it returns criteria attached to it and if the value parameter is true," +
            "retrieves all the values associated with the criteria of a category")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 404, message = "Category not found")})
    protected ResponseEntity<?> getCriteria(
            @ApiParam(value = "Category identification number")
            @RequestParam(value="id_category",required = false)Long id_category,
             @ApiParam(value = "Category identification number")
             @RequestParam(value="filter",required = false)String filter
            ) {
        if (id_category == null && filter == null)
            return ResponseEntity.ok().body(this.criteriaService.getAllcriteria());
        if (id_category != null && filter==null) {
            if (this.categoryService.getCategoryWithId(id_category) == null)
                throw new ApplicationException(HttpStatus.NOT_FOUND, "Category id not present in database");
            else
                return ResponseEntity.ok().body(this.categoryService.getCategoryCriteria(categoryService.getCategoryWithId(id_category)));
        }
        if (id_category != null && filter.equals(true))
            return ResponseEntity.ok(this.criteriaService.getAllCriteriaAndAllValuesAssociatesToACategory(id_category));
        else
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Bad request.");

    }
    @ApiOperation(value = "Add criteria with a CSV file (delimiter: `;`)  Only last-child category " +
            "can receive criteria."+
            "Line example : "+
            "Long Id_Categorie ; Boolean criteria_mandatory_1 ; name_criteria_1 ; Unity_criteria_1 ; Type_unity_1 ; .... ")
    @PostMapping(consumes = "multipart/form-data",produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File is successful imported and treated. You can find in json response" +
                    " number lines added, the lines not added, and the error lines"),
            @ApiResponse(code = 400, message = "Wrong file format") })
    public ResponseEntity<?> createCriterias(@ApiParam(value = "Your CSV file with the criteria to import." +
            " Cannot be empty. ",required = true)@RequestParam("file") MultipartFile file) {
        if(!file.getOriginalFilename().endsWith("csv")) {
            throw new ApplicationException(HttpStatus.resolve(400), "Wrong file format");
    }
        else {
         return ResponseEntity.ok(this.criteriaService.create(file).toJSONString());
        }
    }



}
