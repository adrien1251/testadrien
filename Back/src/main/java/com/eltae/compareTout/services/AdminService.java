package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        return "ok";
    }
}
