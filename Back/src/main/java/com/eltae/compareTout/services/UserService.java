package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.dto.user.UserDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.exceptions.NotFoundException;
import com.eltae.compareTout.repositories.UserRepository;
import com.eltae.compareTout.security.AuthAuthorityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid email or password.");
        }

        if (user.get() instanceof Supplier){
            if(((Supplier) user.get()).getValidationDate() == null) {
                throw new ApplicationException(HttpStatus.FORBIDDEN, "Your account reach the process of validation. Be patient");
            }
        }

        List<GrantedAuthority> grantedAuths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(AuthAuthorityEnum.getRole(user.get()).toString());

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                grantedAuths);
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                    () -> new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid email or password.")
                );
    }

    public UserDto create(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        userDto = userConverter.entityToDto(this.userRepository.save(user));
        return userDto;
    }

    public UserDto update(UserDto userDto) throws NotFoundException {
        //TODO : Update user
        return userDto;
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id).map(this.userConverter::entityToDtoMinimumParams)
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST,"Invalid username or password."));
    }

    public UserDto getByToken(String token) {
        Optional<User> user = userRepository.findByResetToken(token);
        if(!user.isPresent()){
            throw new ApplicationException(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid username or password.");
        }
        return this.userConverter.entityToDto(user.get());
    }

    public List<UserDto> getAllUsers() {
        return userConverter.entityListToDtoList(userRepository.findAll());
    }
}
