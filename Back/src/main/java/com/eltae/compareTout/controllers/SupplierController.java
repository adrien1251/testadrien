package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.SupplierService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 500, message = "No value present")})
    protected ResponseEntity<?> getSuppliers(
            @ApiParam(value = "Identification number of the supplier")
            @RequestParam(value="id_supplier",required = false)Long id)
            {
        if(id==null)
            return ResponseEntity.ok().body(this.supplierService.getAllSuppliers());
        else
                return ResponseEntity.ok().body(this.supplierService.getSupplierInfo(id));
    }


    @ApiOperation(value = "Create a supplier account. This account will be activate by the administrator.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The supplier account has correctly been created"),
            @ApiResponse(code = 500, message = "ThIs email is already attached to an account")})
    @PostMapping(produces="application/json")
    public ResponseEntity<?> createSupplier(@RequestBody SupplierInscriptionDto supDto) {
        return ResponseEntity.status(200).body(this.supplierService.create(supDto));
    }

    @ApiOperation(value = "Update your supplier account.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The supplier account has correctly been updated"),
            @ApiResponse(code = 500, message = "Supplier not found")})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateSupplier(@RequestBody SupplierDto supDto) {
        return ResponseEntity.status(200).body(this.supplierService.updateSupplier(supDto));
    }


}
