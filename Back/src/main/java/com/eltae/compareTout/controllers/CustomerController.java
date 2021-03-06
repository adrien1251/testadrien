package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.customer.CustomerInscriptionDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.CUSTOMER)
@Api(value = "Customers", description = "Customers gesture", tags = {"Customers"})
public class CustomerController extends ExceptionCatcher {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @ApiOperation(value = "Create a customer account. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The customer account has correctly been created"),
            @ApiResponse(code = 409, message = "This email already exist")})
    @PostMapping(produces="application/json")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerInscriptionDto cusDto) {
        return ResponseEntity.status(200).body(this.customerService.create(cusDto));
    }

    @ApiOperation(value = "Update your customer account.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The customer account has correctly been updated"),
            @ApiResponse(code = 404, message = "Supplier not found")})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(200).body(this.customerService.updateCustomer(customerDto));
    }


    @ApiOperation(value = "Produces json with information about a customer. You must indicate a customer " +
            "identification number.")
    @GetMapping(produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request is successfully treated"),
            @ApiResponse(code = 400, message = "Query parameter missing"),
            @ApiResponse(code = 404, message = "Customer identification not in database.")})
    protected ResponseEntity<?> getCustomer(
            @ApiParam(value = "Identification number of the supplier")
            @RequestParam(value="customer_id",required = true)Long id)
            {
                if(id!=null)
                return ResponseEntity.ok().body(this.customerService.getCustomerInfo(id));
                else
                    throw new ApplicationException(HttpStatus.BAD_REQUEST, "Please check your request.");
    }
}
