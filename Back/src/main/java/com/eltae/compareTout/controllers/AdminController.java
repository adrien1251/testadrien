package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.ADMIN)
@Api(value = "Admin", description = "Admin gesture", tags = {"Admins"})
public class AdminController extends ExceptionCatcher {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.status(201).body(this.adminService.create(adminDto));
    }




}
