package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.repositories.AdminRepository;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private AdminRepository adminRepository;
    private AdminConverter adminConverter;
    private SupplierRepository supplierRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, AdminConverter adminConverter, SupplierRepository supplierRepository){
        this.adminRepository = adminRepository;
        this.adminConverter = adminConverter;
        this.supplierRepository = supplierRepository;
    }

    public AdminDto create(AdminDto adminDto) {
        Admin admin = adminConverter.dtoFromFrontEntity(adminDto);
        return adminConverter.entityToDto(this.adminRepository.save(admin));
    }

    public AdminDto update(AdminDto adminDto) {
        Optional<Admin> a = adminRepository.findById(adminDto.getId());
        if (a.isPresent()){
            Admin admin = this.adminConverter.dtoToEntity(adminDto);
            return adminConverter.entityToDto(this.adminRepository.save(admin));
        }
        return null;
    }


    public AdminDto getAdminWithId(long idAdmin) {
        return  null;
    }


}
