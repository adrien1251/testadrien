package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.Customer.CustomerConverter;
import com.eltae.compareTout.converter.Customer.CustomerInscriptionConverter;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.customer.CustomerInscriptionDto;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.Customer.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @SpyBean
    @Autowired
    CustomerConverter customerConverter;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    CustomerInscriptionConverter customerInscriptionConverter;

    @MockBean
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Customer createEntityUser(Long i) {
        return Customer.builder()
                .id(i)
                .email("test" + i + "@test" + i + ".fr")
                .firstName("test" + i)
                .lastName("TEST" + i)
                .phoneNum("0102030405")
                .password("password" + i + "Test").build();
    }

    @Test
    public void testCreateCustomer() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerInscriptionDto customerEntry = CustomerInscriptionDto.builder()
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();

        //Effective
        Customer customerReturnByEntity = this.customerInscriptionConverter.dtoToEntity(customerEntry);
        Customer entryCustomerSave = customerReturnByEntity.clone();
        Customer customerReturnBySave = entryCustomerSave.clone();
        customerReturnByEntity.setId(1L);
        CustomerDto customerExpected = CustomerDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
        //Mocks
        Mockito.when(this.bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("randomPassword");
        Mockito.when(this.customerRepository.save(entryCustomerSave)).thenReturn(customerReturnBySave);

        //Call
        CustomerDto userEffective = customerService.create(customerEntry);

        //Asset
        assertEquals(customerExpected, userEffective);
    }


    @Test (expected = ApplicationException.class)
    public void testCreateCustomerThrowExceptionWhenEmailAlreadyExists() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerInscriptionDto customerEntry = CustomerInscriptionDto.builder()
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();

        //Effective
        Customer customerReturnByEntity = this.customerInscriptionConverter.dtoToEntity(customerEntry);
        Customer entryCustomerSave = customerReturnByEntity.clone();
        Customer customerReturnBySave = entryCustomerSave.clone();
        customerReturnByEntity.setId(1L);
        CustomerDto customerExpected = CustomerDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
        //Mocks
        Mockito.when(this.customerRepository.findByEmail(customerEntry.getEmail())).thenReturn(Optional.of(Customer.builder().build()));

        //Call
        customerService.create(customerEntry);

    }

    @Test
    public void testUpdateCustomer() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerDto customerEntry = CustomerDto.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();


        //Effective
        Customer customerReturnByEntity = this.customerConverter.dtoToEntity(customerEntry);
        Customer customerReturnedBySave = customerReturnByEntity.clone();
        CustomerDto customerUpdated = CustomerDto.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();
        //Mocks
        Mockito.when(this.customerRepository.findByIdAndDiscriminatorValue(100L,"CUSTOMER")).thenReturn(Optional.of(Customer.builder().build()));
        Mockito.when(this.customerRepository.save(customerReturnByEntity)).thenReturn(customerReturnedBySave);
        //Call
        CustomerDto customerReturned = customerService.updateCustomer(customerEntry);

        //Asset
        assertEquals(customerUpdated, customerReturned);
    }

    @Test (expected = ApplicationException.class)
    public void testUpdateCustomerWhenCustomerDoesntNotExist() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerDto customerEntry = CustomerDto.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();


        //Effective
        Customer customerReturnByEntity = this.customerConverter.dtoToEntity(customerEntry);
        Customer customerReturnedBySave = customerReturnByEntity.clone();

        //Mocks
        Mockito.when(this.customerRepository.findByIdAndDiscriminatorValue(100L,"CUSTOMER")).thenReturn(Optional.empty());
        //Call
        customerService.updateCustomer(customerEntry);

    }

    @Test
    public void testGetCustomerInfo() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerDto customerEntry = CustomerDto.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();


        //Effective
        Optional<Customer> optionalCustomer = Optional.of(Customer.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build());
        //Mocks
        Mockito.when(customerRepository.findById(100L)).thenReturn(optionalCustomer);
        //Call
        CustomerDto customerReturned = customerService.getCustomerInfo(100L);

        //Asset
        assertEquals(customerEntry, customerReturned);
    }


    @Test (expected = ApplicationException.class)
    public void testGetCustomerInfoThrowExceptionWhenCustomerNotFound() throws CloneNotSupportedException {
        //Entry
        Long customerIdEntry = 100L;

        CustomerDto customerEntry = CustomerDto.builder()
                .id(customerIdEntry)
                .password("mdp")
                .email("test" + customerIdEntry + "@test" + customerIdEntry + ".fr")
                .firstName("test" + customerIdEntry)
                .lastName("TEST" + customerIdEntry)
                .password("password" + customerIdEntry + "Test").build();


        //Effective
        Customer customerReturnByEntity = this.customerConverter.dtoToEntity(customerEntry);
        Customer customerReturnedBySave = customerReturnByEntity.clone();
        //Mocks
        Mockito.when(this.customerRepository.findByIdAndDiscriminatorValue(100L,"CUSTOMER")).thenReturn(Optional.empty());
        //Call
        CustomerDto customerReturned = customerService.getCustomerInfo(100L);

        //Asset
        assertEquals(customerEntry, customerReturned);
    }
}
