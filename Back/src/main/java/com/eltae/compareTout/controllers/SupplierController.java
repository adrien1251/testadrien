package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.SupplierService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.SUPPLIER)
@Api(value = "Supplier", description = "Supplier gesture", tags = {"Supplier"})
public class SupplierController extends ExceptionCatcher {
    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @ApiOperation(value = "Produces json with information of all suppliers if no query parameters. If an identification number " +
            "of a supplier is provided, it will return only its information")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 460, message = "No value present")})
    protected ResponseEntity<?> getSuppliers(@ApiParam(value = "Identification number of the supplier") @RequestParam(value="id_supplier",required = false)Long id) {
        Gson gson = new Gson();
        if(id==null) {
            return ResponseEntity.ok().body(gson.toJson(this.supplierService.getAllSuppliers()));
        }
        else{
            if(this.supplierService.getSupplierWithId(id)==false)
                return ResponseEntity.status(460).body(gson.toJson("Supplier id not present in database"));
            else {
                return ResponseEntity.ok().body(gson.toJson(this.supplierService.getSupplierInfo(id)));
            }
        }
    }
}
