package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.user.UserDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.reflections.util.ConfigurationBuilder.build;

@Component
public class UserConverter extends GenericsConverter<User, UserDto> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .resetToken(user.getResetToken())
                .build();
    }

    public UserDto entityToDtoMinimumParams(User user) {
        if(user instanceof Supplier){
            return SupplierDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .webSite(((Supplier) user).getWebSite())
                    .siret(((Supplier) user).getSiret())
                    .build();
        } else if(user instanceof Customer){
            return CustomerDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNum(((Customer) user).getPhoneNum())
                    .sexe(((Customer) user).getSexe())
                    .birthday(((Customer) user).getBirthday())
                    .build();
        } else if(user instanceof Admin){
            return AdminDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNum(((Admin) user).getPhoneNum())
                    .build();
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public List<UserDto> entityListToDtoListMinimumParams(List<User> entityList){
        List<UserDto> dtoList = new ArrayList<>();
        for(User entity : entityList){
            dtoList.add(entityToDtoMinimumParams(entity));
        }
        return dtoList;
    }

    @Override
    public User dtoToEntity(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());

        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .resetToken(userDto.getResetToken())
                .build();

    }
}
