package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.security.AuthAuthorityEnum;
import com.eltae.compareTout.services.SupplierService;
import com.eltae.compareTout.utils.UtilsAuth;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.SUPPLIER)
@Api(value = "Suppliers", description = "Suppliers gesture", tags = {"Suppliers"})
public class SupplierController extends ExceptionCatcher {
    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }



    @ApiOperation(value = "Create a supplier account. This account will be activate by the administrator.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The supplier account has correctly been created"),
            @ApiResponse(code = 409, message = "Conflict: ThIs email is already attached to an account")})
    @PostMapping(produces="application/json")
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierInscriptionDto supDto) {
        return ResponseEntity.status(200).body(this.supplierService.create(supDto));
    }

    @ApiOperation(value = "Update your supplier account. If a ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The supplier account has correctly been updated"),
            @ApiResponse(code = 404, message = "Supplier not found")})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateSupplier(@RequestBody SupplierDto supDto) {
        return ResponseEntity.status(200).body(this.supplierService.updateSupplier(supDto));
    }

    @ApiOperation(value = "Confirm a supplier account")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The supplier account has correctly been validated"),
            @ApiResponse(code = 400, message = "Invalid supplier ID"),
            @ApiResponse(code = 412, message = "Already validated supplier")})
    @PatchMapping (produces = "application/json",value="/{supplierId}")
    public ResponseEntity<SupplierDto> validationSupplierAccount(@PathVariable Long supplierId) {
        return ResponseEntity.status(201).body(this.supplierService.confirmSupplierAccount(supplierId));
    }


    @ApiOperation(value = "Retrieve all suppliers with query parameter filter=All; " +
            "Retrieve supplier's with account to validate with query parameter " +
            " filter= notValidate; Retrieve a specific supplier with id identification number." +
            " You can't use both query parameters.")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 404, message = "Supplier identification number not found"),
            @ApiResponse(code = 400, message = "Bad request")})
    protected ResponseEntity<?> getSuppliers(
            @ApiParam(value = "Identification number of the supplier")
            @RequestParam(value="id_supplier",required = false)Long id,
            @ApiParam(value = "notValidate => for supplier not validate, All => for all suppliers")
            @RequestParam(value="filter",required = false)String filter)
    {
        if(id==null && filter.equals("All"))
            return ResponseEntity.ok().body(this.supplierService.getAllSuppliers());
        else {
            if(id==null && filter.equals("notValidate")) {
                if(!UtilsAuth.actualUserHaveAuthorities(AuthAuthorityEnum.ROLE_ADMIN)){
                    throw new ApplicationException(HttpStatus.UNAUTHORIZED, "You are not authorized to do this action");
                }
                return ResponseEntity.status(201).body(this.supplierService.getAllSuppliersNotValidate());

            } else {
                if (id != null && filter == null)
                    return ResponseEntity.ok().body(this.supplierService.getSupplierInfo(id));
                else
                    throw new ApplicationException(HttpStatus.BAD_REQUEST, "Please check your request.");
            }
        }
    }

}
