package com.eltae.compareTout.controllers;
import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.CriteriaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(Routes.CRITERIA)
public class CriteriaController   extends ExceptionCatcher {

    private CriteriaService criteriaService;

    public CriteriaController(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    @ApiOperation(value = "Liste des produits d'une catégorie pour les critères définis ")
    @GetMapping(value="/")
    public ResponseEntity<List<ShortProductDto>> getProductsCategory(@RequestParam Long id, @RequestParam List<Long> crit) {
        return ResponseEntity.status(201).body(this.criteriaService.getProductsCriteria(id,crit));
    }
}
