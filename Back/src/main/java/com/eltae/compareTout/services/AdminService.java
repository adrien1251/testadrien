package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.AdminConverter;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private AdminRepository adminRepository;
    private AdminConverter adminConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminService(
            AdminRepository adminRepository,
            AdminConverter adminConverter,
            @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.adminConverter = adminConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AdminDto create(AdminDto adminDto) {
        Admin admin = adminConverter.dtoFromFrontEntity(adminDto);
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new ApplicationException(HttpStatus.CONFLICT, "This email already exist");
        }
        return adminConverter.entityToDto(this.adminRepository.save(admin));
    }

    public AdminDto update(AdminDto adminDto) {
        Optional<Admin> a = adminRepository.findById(adminDto.getId());
        if (a.isPresent()) {
            Admin admin = this.adminConverter.dtoToEntity(adminDto);
            return adminConverter.entityToDto(this.adminRepository.save(admin));
        } else
            throw new ApplicationException(HttpStatus.NOT_FOUND, "invalid admin ID");
    }

    public AdminDto getAdminWithId(Long idAdmin) {
        return adminConverter.entityToDto(adminRepository
                .findById(idAdmin)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "invalid admin ID")));
    }


}
