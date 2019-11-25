package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private AdminRepository adminRepository;
    private AdminConverter adminConverter;

    @Autowired
    public AdminService(AdminRepository adminRepository, AdminConverter adminConverter){
        this.adminRepository = adminRepository;
        this.adminConverter = adminConverter;
    }

    public String create(AdminDto adminDto) {
        Admin a = adminConverter.dtoFromFrontEntity(adminDto);
        this.adminRepository.save(a);
        return "Insert successful";
    }

    public boolean update(AdminDto adminDto) {
        Optional<Admin> a = adminRepository.findById(adminDto.getId());
        if (a.isPresent()){
            Admin admin = a.get();
            admin.setPhoneNum(adminDto.getPhoneNum());
            admin.setCreationDate(adminDto.getCreationDate());
            admin.setEmail(adminDto.getEmail());
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());
            admin.setPassword(adminDto.getPassword());
            admin.setResetToken(adminDto.getResetToken());
            this.adminRepository.save(admin);
            return true;

        }
        return false;
    }


    public AdminDto getAdminWithId(long idAdmin) {
        return  null;
    }
}
