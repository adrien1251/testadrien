package com.eltae.compareTout.controllers;
import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.CriteriaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Routes.CRITERIA)
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

    @ApiOperation(value = "Ajout de critères par fichier csv (delimiter: ';')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Integer> createCriterias(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(201).body(this.criteriaService.create(file));
    }



}
