package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.Customer.CustomerConverter;
import com.eltae.compareTout.converter.Customer.CustomerInscriptionConverter;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.customer.CustomerInscriptionDto;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.Customer.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final CustomerInscriptionConverter customerInscriptionConverter;



    public CustomerService(CustomerRepository customerRepository,
                           CustomerConverter customerConverter,
                           CustomerInscriptionConverter customerInscriptionConverter){

        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.customerInscriptionConverter = customerInscriptionConverter;
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findByDiscriminatorValue("CUSTOMER");
    }


    public boolean isCustomer (Long id) {
        return this.customerRepository.findByIdAndDiscriminatorValue  (id,"CUSTOMER").get()!=null;
        //return this.customerRepository.findById(id).get()!=null;
        }

    public CustomerDto getCustomerInfo(Long id) {
        System.out.println(this.isCustomer(id));
        if(this.isCustomer(id))
        return this.customerConverter.entityToDto( this.customerRepository.findByIdAndDiscriminatorValue
               (id,"CUSTOMER").get());
        else
            return null;
    }

   // public Customer getEntityCustomer(Long id){
     //   return this.customerRepository.findByIdAndDiscriminatorValue
      //          (id,"CUSTOMER").get();
  //  }

    public Optional<Customer> findCustomerByEmail(String email){
        return this.customerRepository.findByEmail(email);
    }

    public CustomerDto create(CustomerInscriptionDto cusDto) {
        Customer customer = customerInscriptionConverter.dtoToEntity(cusDto);
        if(findCustomerByEmail(customer.getEmail()).isPresent()){
            throw new ApplicationException(HttpStatus.CONFLICT, "This email already exist");
        }
        return customerConverter.entityToDto(this.customerRepository.save(customer));
    }



    public String updateCustomer(CustomerDto cusDto) {
        if(this.isCustomer(cusDto.getId())) {
            Customer customer = this.customerConverter.dtoToEntity(cusDto);
            this.customerRepository.save(customer);
            return "Customer has been correctly updated";
        }
        else
            return "Customer not found";
    }


}
