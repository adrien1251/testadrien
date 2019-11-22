package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.AdminDto;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.AdminService;
import com.eltae.compareTout.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.ADMIN)
@Api(value = "Admin", description = "Admin gesture", tags = {"Admins"})
public class AdminController extends ExceptionCatcher {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @ApiOperation(value = "Create a new admin profile in database.")
    @PostMapping(produces = "application/json", value = "/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.status(201).body(this.adminService.create(adminDto));
    }

    @ApiOperation(value = "Update an admin profile in database.")
    @PostMapping(produces = "application/json", value = "/update")
    public ResponseEntity<?> updateAdminProfile(@RequestBody AdminDto adminDto) {
        if (!this.adminService.update(adminDto)) {
            throw new ApplicationException(HttpStatus.resolve(400), "Update failed");
        }
        else
            return ResponseEntity.status(200).body("Admin profile updated");
    }


}
