package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.Customer.CustomerConverter;
import com.eltae.compareTout.converter.Customer.CustomerInscriptionConverter;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.customer.CustomerInscriptionDto;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.Customer.CustomerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final CustomerInscriptionConverter customerInscriptionConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public CustomerService(CustomerRepository customerRepository,
                           CustomerConverter customerConverter,
                           CustomerInscriptionConverter customerInscriptionConverter,
                           @Lazy BCryptPasswordEncoder bCryptPasswordEncoder1) {

        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.customerInscriptionConverter = customerInscriptionConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

<<<<<<< HEAD
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findByDiscriminatorValue("CUSTOMER");
    }



    public boolean isCustomer(Long id) {
        return this.customerRepository.findByIdAndDiscriminatorValue(id, "CUSTOMER").get() != null;
      }

    public CustomerDto getCustomerInfo(Long id) {
        return customerConverter.entityToDto(customerRepository
                .findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Invalid customer ID")));
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return this.customerRepository.findByEmail(email);
    }

    public CustomerDto create(CustomerInscriptionDto cusDto) {
        Customer customer = customerInscriptionConverter.dtoToEntity(cusDto);
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        if (this.customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new ApplicationException(HttpStatus.CONFLICT, "This email already exist");
        }
        return customerConverter.entityToDto(this.customerRepository.save(customer));
    }

    public CustomerDto updateCustomer(CustomerDto cusDto) {
       if (this.isCustomer(cusDto.getId())) {
            Customer customer = this.customerConverter.dtoToEntity(cusDto);
            this.customerRepository.save(customer);
            return customerConverter.entityToDto((this.customerRepository.save(customer)));
        } else
            throw new ApplicationException(HttpStatus.NOT_FOUND, "invalid customer ID");
    }
}
