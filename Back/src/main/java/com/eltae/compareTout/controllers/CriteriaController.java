package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.CriteriaService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Routes.CRITERIA)
@Api(value = "Criteria", description = "Criteria gesture", tags = {"Criteria"})
public class CriteriaController   extends ExceptionCatcher {

    private CriteriaService criteriaService;

    public CriteriaController(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    @ApiOperation(value = "Liste des produits d'une catégorie pour les critères définis ")
    @GetMapping(value="/")
    public ResponseEntity<List<ShortProductDto>> getProductsCriteria(@RequestParam Long id, @RequestParam List<Long> crit) {
        return ResponseEntity.status(201).body(this.criteriaService.getProductsCriteria(id,crit));
    }


    @ApiOperation(value = "Liste des produits d'une catégorie pour les critères et valeur définis  ")
    @GetMapping(value="/strict")
    public ResponseEntity<List<ShortProductDto>> getProductsStrictCriteria(@RequestParam Long id, @RequestParam Long[] idCrit,
                                                                           @RequestParam String[] valuesCrit) {

        return ResponseEntity.status(201).body(this.criteriaService.getProductsStrictCriteria(id,idCrit,valuesCrit));
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


    /*
    @ApiOperation(value = "Ajout de critères par fichier csv (delimiter: ';')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Integer> createCriterias(@ApiParam(value = "Your CSV file with the categories to import." +
            " Cannot be empty. ",required = true)@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(201).body(this.criteriaService.create(file));
    }
*/


}
