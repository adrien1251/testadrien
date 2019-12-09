package com.eltae.compareTout.converter.Customer;


import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.customer.CustomerInscriptionDto;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.repositories.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerInscriptionConverter extends GenericsConverter<Customer, CustomerInscriptionDto> {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerInscriptionDto entityToDto(Customer customer) {
                return CustomerInscriptionDto.builder()
                        .email(customer.getEmail())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getFirstName())
                        .birthday(customer.getBirthday())
                        .sexe(customer.getSexe())
                        .phoneNum(customer.getPhoneNum())
                        .build();
    }

    @Override
    public Customer dtoToEntity(CustomerInscriptionDto supplierInscriptionDto) {
        Customer customer = Customer.builder()
                .birthday(supplierInscriptionDto.getBirthday())
                .password(supplierInscriptionDto.getPassword())
                .lastName(supplierInscriptionDto.getLastName())
                .firstName(supplierInscriptionDto.getFirstName())
                .email(supplierInscriptionDto.getEmail())
                .phoneNum(supplierInscriptionDto.getPhoneNum())
                .creationDate(LocalDate.now())
                .sexe(supplierInscriptionDto.getSexe())
                .build();
        return customer;
    }



    public List<CustomerInscriptionDto> entityListToDtoListSup(List<Customer> all) {
        List<CustomerInscriptionDto> result=new ArrayList<>();
        for(Customer s:all)
            result.add(entityToDto(s));
        return result;
    }
    public Customer dtoFromFrontEntity(CustomerInscriptionDto supDto) {
        Customer customer = Customer.builder()
                .firstName(supDto.getFirstName())
                .lastName(supDto.getLastName())
                .email(supDto.getEmail())
                .password(supDto.getPassword())
                .creationDate(LocalDate.now())
                .birthday(supDto.getBirthday())
                .sexe(supDto.getSexe())
                .phoneNum(supDto.getPhoneNum())
                .password(supDto.getPassword())
                .build();
        return customer;

    }
}
