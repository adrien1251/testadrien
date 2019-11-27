package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.InsertionErrorsDto;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.AdminService;
import com.eltae.compareTout.services.InsertionErrorsService;
import com.eltae.compareTout.services.ProductService;
import com.eltae.compareTout.services.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.ADMIN)
@Api(value = "Admin", description = "Admin gesture", tags = {"Admins"})
public class AdminController extends ExceptionCatcher {

    private AdminService adminService;
    private SupplierService supplierService;
    private InsertionErrorsService insertionErrorsService;


    @Autowired
    public AdminController(AdminService adminService, SupplierService supplierService, InsertionErrorsService insertionErrorsService) {
        this.supplierService = supplierService;
        this.adminService = adminService;
        this.insertionErrorsService = insertionErrorsService;
    }


    @ApiOperation(value = "Create a new admin profile in database.")
    @PostMapping(produces = "application/json", value = "/")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.status(201).body(this.adminService.create(adminDto));
    }

    @ApiOperation(value = "Update an admin profile in database.")
    @PostMapping(produces = "application/json", value = "/update")
    public ResponseEntity<AdminDto> updateAdminProfile(@RequestBody AdminDto adminDto) {
        AdminDto admin = this.adminService.update(adminDto);
        if (admin == null) {
            throw new ApplicationException(HttpStatus.resolve(400), "Update failed");
        } else
            return ResponseEntity.status(200).body(admin);
    }

    @ApiOperation(value = "Confirm a supplier account")
    @PostMapping(produces = "application/json", value = "/validateSupplierAccount")
    public ResponseEntity<SupplierDto> validationSupplierAccount(@RequestBody SupplierDto supplierDto) {
        SupplierDto supplier = this.supplierService.confirmSupplierAccount(supplierDto);
        if (supplier == null)
            throw new ApplicationException(HttpStatus.resolve(400), "Validation failed");
        return ResponseEntity.status(201).body(supplier);
    }

    @ApiOperation(value = "Get all suppliers accounts to valid")
    @GetMapping(produces = "application/json", value = "/getAllSuppliersAccountToValid")
    public ResponseEntity<List<SupplierDto>> getAllSuppliersToValid() {
        return ResponseEntity.status(201).body(this.supplierService.getAllSuppliersNotValidate());
    }

    @ApiOperation(value = "Recover all errors that occurred during products insertion")
    @GetMapping(produces = "application/json", value = "/getAllProductsInsertionErrors")
    public ResponseEntity<List<InsertionErrorsDto>> getAllProductsInsertionErrors() {
        return ResponseEntity.status(201).body(this.insertionErrorsService.getAllProductsInsertionErrors());
    }
}
