package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.user.LoginDto;
import com.eltae.compareTout.dto.user.UserDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, UserConverter userConverter, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto login(LoginDto login) {
        Optional<User> dbUser = userRepository.findByEmail(login.getEmail());
        if (!dbUser.isPresent() ||
                login.getPassword() == null ||
                !bCryptPasswordEncoder.matches(login.getPassword(), dbUser.get().getPassword())) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Wrong email or password");
        }

        return this.userConverter.entityToDtoMinimumParams(dbUser.get());
    }
}
