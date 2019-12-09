package com.eltae.compareTout.controllers;

import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.AdminRepository;
import com.eltae.compareTout.services.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminControllerTest {
    @Autowired
    AdminController adminController;

    @SpyBean
    @Autowired
    AdminService adminService;

    @SpyBean
    @Autowired
    AdminConverter adminConverter;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test(expected = ApplicationException.class)
    public void testUpdateAdminThrowExceptionWhenAdminDoesntExist() throws CloneNotSupportedException {
        //Entry
        Long adminIdEntry = 100L;
        AdminDto adminEntry = AdminDto.builder()
                .id(adminIdEntry)
                .email("test" + adminIdEntry + "@test" + adminIdEntry + ".fr")
                .firstName("test" + adminIdEntry)
                .lastName("TEST" + adminIdEntry)
                .phoneNum("0102030405")
                .password("password" + adminIdEntry + "Test").build();


        //Call
        adminController.updateAdmin(adminEntry);
    }

}
