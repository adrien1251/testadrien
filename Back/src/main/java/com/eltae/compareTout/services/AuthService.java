package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.user.LoginDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final SupplierConverter supplierConverter;
    private final SupplierService supplierService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, SupplierService supplierService, UserConverter userConverter, SupplierConverter supplierConverter, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.supplierConverter = supplierConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.supplierService = supplierService;
    }

    public SupplierDto login(LoginDto login) {
        Optional<Supplier> dbUser = supplierService.findSupplierByEmail(login.getEmail());
        if (!dbUser.isPresent() || login.getPassword() == null ||
                !bCryptPasswordEncoder.matches(login.getPassword(), dbUser.get().getPassword())) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Wrong email or password");
        }

        return supplierConverter.entityToDto(dbUser.get());
    }
}
