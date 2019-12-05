package com.eltae.compareTout.controllers;


import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.InsertionErrorsDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.InsertionErrorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.INSERTIONERRORS)
@Api(value = "InsertionErrors", description = "Insertions errors gesture", tags = {"Insertions"})


public class InsertionErrorsController extends ExceptionCatcher {


    private InsertionErrorsService insertionErrorsService;


    @Autowired
    public  InsertionErrorsController(InsertionErrorsService insertionErrorsService){
        this.insertionErrorsService=insertionErrorsService;
    }


    @ApiOperation(value = "Recover all errors that occurred during products insertion")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Request is successfully treated"),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public ResponseEntity<List<InsertionErrorsDto>> getAllInsertionErrors() {
        return ResponseEntity.status(201).body(this.insertionErrorsService.getAllProductsInsertionErrors());
    }



}
