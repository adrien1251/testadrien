package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.AdminRepository;
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
public class AdminServiceTest {

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

    private Admin createEntityUser(Long i) {
        return Admin.builder()
                .id(i)
                .email("test" + i + "@test" + i + ".fr")
                .firstName("test" + i)
                .lastName("TEST" + i)
                .phoneNum("0102030405")
                .password("password" + i + "Test").build();
    }

    @Test
    public void testCreateAdmin() throws CloneNotSupportedException {
        //Entry
        Long adminIdEntry = 100L;

        AdminDto adminEntry = AdminDto.builder()
                .id(adminIdEntry)
                .email("test" + adminIdEntry + "@test" + adminIdEntry + ".fr")
                .firstName("test" + adminIdEntry)
                .lastName("TEST" + adminIdEntry)
                .phoneNum("0102030405")
                .password("password" + adminIdEntry + "Test").build();

        //Effective
        Admin adminReturnByEntity = this.adminConverter.dtoToEntity(adminEntry);
        Admin entryAdminSave = adminReturnByEntity.clone();
        Admin adminReturnBySave = entryAdminSave.clone();
        adminReturnByEntity.setId(1L);
        AdminDto adminExpected = AdminDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
        //Mocks
        Mockito.when(this.bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("randomPassword");
        Mockito.when(this.adminRepository.save(entryAdminSave)).thenReturn(adminReturnBySave);

        //Call
        AdminDto userEffective = adminService.create(adminEntry);

        //Asset
        assertEquals(adminExpected, userEffective);
    }

    @Test(expected = ApplicationException.class)
    public void testCreateAdminThrowExceptionWhenAOtherUserHaveTheSameEmail() throws CloneNotSupportedException {
        //Entry
        Long adminIdEntry = 100L;
        AdminDto adminEntry = AdminDto.builder()
                .id(adminIdEntry)
                .email("test" + adminIdEntry + "@test" + adminIdEntry + ".fr")
                .firstName("test" + adminIdEntry)
                .lastName("TEST" + adminIdEntry)
                .phoneNum("0102030405")
                .password("password" + adminIdEntry + "Test").build();

        //Effective
        Admin adminReturnByEntity = this.adminConverter.dtoToEntity(adminEntry);
        Admin entryAdminSave = adminReturnByEntity.clone();
        Admin adminReturnBySave = entryAdminSave.clone();
        adminReturnByEntity.setId(1L);
        AdminDto adminExpected = AdminDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();

        Optional<Admin> adminOptional = Optional.ofNullable(adminReturnBySave);
        //Mocks
        Mockito.when(this.adminRepository.findByEmail(adminEntry.getEmail())).thenReturn(adminOptional);

        //Call
        adminService.create(adminEntry);
    }


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
        adminService.update(adminEntry);
    }


    @Test
    public void testFindAdmin() throws CloneNotSupportedException {
        //Entry
        Long adminIdEntry = 100L;

        AdminDto adminEntry = AdminDto.builder()
                .id(adminIdEntry)
                .email("test" + adminIdEntry + "@test" + adminIdEntry + ".fr")
                .firstName("test" + adminIdEntry)
                .lastName("TEST" + adminIdEntry)
                .phoneNum("0102030405")
                .password("password" + adminIdEntry + "Test").build();

        //Effective
        Admin adminReturnByEntity = this.adminConverter.dtoToEntity(adminEntry);
        Admin entryAdminSave = adminReturnByEntity.clone();
        Admin adminReturnBySave = entryAdminSave.clone();
        AdminDto adminExpected = AdminDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
        Optional<Admin> adminOptional = Optional.ofNullable(adminReturnBySave);
        //Mocks
        Mockito.when(this.adminRepository.findById(adminIdEntry)).thenReturn(adminOptional);

        //Call
        AdminDto userEffective = adminService.getAdminWithId(adminIdEntry);

        //Asset
        assertEquals(adminExpected, userEffective);
    }


    @Test (expected = ApplicationException.class)
    public void testFindAdminThrowAnExceptionWhenTheAdminDoesntNotExist() throws CloneNotSupportedException {
        //Entry
        Long adminIdEntry = 100L;

        //Call
        adminService.getAdminWithId(adminIdEntry);
    }

}
