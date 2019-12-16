package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.services.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Routes.ADMIN)
@Api(value = "Administrators", description = "Administrator gesture", tags = {"Administrators"})
public class AdminController extends ExceptionCatcher {

    private AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @ApiOperation(value = "Create a new administrator profile in database.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administrator account successfully  created"),
            @ApiResponse(code = 409, message = "Conflict : This email already exist")})
    @PostMapping(produces = "application/json")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.status(201).body(this.adminService.create(adminDto));
    }

    @ApiOperation(value = "Update an administrator profile in database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Administrator account successfully  updated"),
            @ApiResponse(code = 400, message = "Update failed"),
            @ApiResponse(code = 404, message = "Not found")})
    @PutMapping(produces = "application/json")
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto) {
        AdminDto admin = this.adminService.update(adminDto);
        if (admin == null) {
            throw new ApplicationException(HttpStatus.resolve(400), "Update failed");
        } else
            return ResponseEntity.status(200).body(admin);
    }




}
