package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminConverter extends GenericsConverter<Admin, AdminDto> {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminDto entityToDto(Admin admin) {
        return AdminDto.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .resetToken(admin.getResetToken())
                .build();
    }

    @Override
    public Admin dtoToEntity(AdminDto adminDto) {
        return null;
    }

    public AdminDto entityToDtoMinimumParams(Admin admin) {
        return AdminDto.builder()
                .id(admin.getId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .build();
    }

    public List<AdminDto> entityListToDtoListMinimumParams(List<Admin> entityList){
        List<AdminDto> dtoList = new ArrayList<>();
        for(Admin entity : entityList){
            dtoList.add(entityToDtoMinimumParams(entity));
        }
        return dtoList;
    }


    public Admin dtoFromFrontEntity(AdminDto adminDto) {
        Admin a = Admin.builder()
                .phoneNum(adminDto.getPhoneNum())
                .firstName(adminDto.getFirstName())
                .lastName(adminDto.getLastName())
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .creationDate(LocalDate.now())
                .build();
        return a;

    }
}
