package com.eltae.compareTout.converter.Customer;

import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.repositories.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerConverter extends GenericsConverter<Customer, CustomerDto> {

    @Autowired
    private CustomerRepository customerRepository;


    public CustomerDto entityToDto(Customer customer) {
                return CustomerDto.builder()
                        .id(customer.getId())
                        .email(customer.getEmail())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getFirstName())
                        .birthday(customer.getBirthday())
                        .phoneNum(customer.getPhoneNum())
                        .creationDate(customer.getCreationDate())
                        .sexe(customer.getSexe())
                        .resetToken(customer.getResetToken())
                        .build();
    }

    @Override
    public Customer dtoToEntity(CustomerDto customerDto) {
            return Customer.builder()
                    .email(customerDto.getEmail())
                    .firstName(customerDto.getFirstName())
                    .lastName(customerDto.getLastName())
                    .password(customerDto.getPassword())
                     .birthday(customerDto.getBirthday())
                    .id(customerDto.getId())
                    .resetToken(customerDto.getResetToken())
                    .phoneNum(customerDto.getPhoneNum())
                    .sexe(customerDto.getSexe())
                    .build();
    }


    public List<CustomerDto> entityListToDtoListSup(List<Customer> all) {
        List<CustomerDto> result=new ArrayList<>();
        for(Customer s:all)
            result.add(entityToDto(s));
        return result;
    }
}
