package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.converter.supplier.SupplierInscriptionConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class SupplierServiceTest {

    @Autowired
    SupplierService supplierService;

    @SpyBean
    @Autowired
    SupplierConverter supplierConverter;

    @SpyBean
    @Autowired
    SupplierInscriptionConverter supplierInscriptionConverter;

    @MockBean
    private SupplierRepository supplierRepository;

    @MockBean
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Supplier createEntityUser(Long i) {
        return Supplier.builder()
                .id(i)
                .email("test" + i + "@test" + i + ".fr")
                .firstName("test" + i)
                .lastName("TEST" + i)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + i + "Test").build();
    }

    @Test
    public void testCreateSupplier() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 100L;
        SupplierInscriptionDto supplierEntry = SupplierInscriptionDto.builder()
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

        //Effective
        Supplier supplierReturnByEntity = this.supplierInscriptionConverter.dtoFromFrontEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        supplierReturnByEntity.setId(1L);
        SupplierDto supplierExpected = SupplierDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .build();
        //Mocks
        Mockito.when(this.bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("randomPassword");
        Mockito.when(this.supplierRepository.save(entrySupplierSave)).thenReturn(supplierReturnBySave);

        //Call
        SupplierDto userEffective = supplierService.create(supplierEntry);

        //Asset
        assertEquals(supplierExpected, userEffective);
    }


    @Test
    public void testCreateSupplierCheckIfTheCreationDateIsCorrect() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 100L;
        SupplierInscriptionDto supplierEntry = SupplierInscriptionDto.builder()
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

        //Effective
        Supplier supplierReturnByEntity = this.supplierInscriptionConverter.dtoFromFrontEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        //Mocks
        Mockito.when(this.supplierRepository.save(entrySupplierSave)).thenReturn(supplierReturnBySave);

        //Call
        SupplierDto userEffective = supplierService.create(supplierEntry);

        //Asset
        assertEquals(LocalDate.now(), userEffective.getCreationDate());
    }


    @Test (expected = ApplicationException.class)
    public void testCreateSupplierCheckIfTheExceptionIsCorrectlyThrowedWhenSupplierEmailAlreadyExist() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 100L;
        SupplierInscriptionDto supplierEntry = SupplierInscriptionDto.builder()
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

        //Effective
        Supplier supplierReturnByEntity = this.supplierInscriptionConverter.dtoFromFrontEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        //Mocks
        Optional<Supplier> supplierOptional = Optional.ofNullable(supplierReturnBySave);
        Mockito.when(this.supplierService.findSupplierByEmail(supplierReturnByEntity.getEmail()))
                .thenReturn(supplierOptional);

        //Call
        supplierService.create(supplierEntry);

    }

    @Test
    public void testConfirmSupplierAccount() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 1L;
        SupplierDto supplierEntry = SupplierDto.builder()
                .id(supplierIdEntry)
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

        //Effective
        Supplier supplierReturnByEntity = this.supplierConverter.dtoToEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        supplierReturnByEntity.setId(1L);
        SupplierDto supplierExpected = SupplierDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .validationDate(LocalDate.now())
                .build();
        //Mocks
        Optional<Supplier> supplierOptional = Optional.ofNullable(supplierReturnBySave);
        Mockito.when(this.supplierRepository.findById(supplierEntry.getId())).thenReturn(supplierOptional);
        Mockito.when(this.supplierRepository.save(supplierOptional.get())).thenReturn(supplierOptional.get());

        //Call
        SupplierDto supplierEffective = supplierService.confirmSupplierAccount(supplierEntry.getId());

        //Asset
        assertEquals(supplierExpected, supplierEffective);
    }


    @Test
    public void testConfirmSupplierAccountCheckIfValidateDateIsCorrect() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 1L;
        SupplierDto supplierEntry = SupplierDto.builder()
                .id(supplierIdEntry)
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

        //Effective
        Supplier supplierReturnByEntity = this.supplierConverter.dtoToEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        supplierReturnByEntity.setId(1L);

        //Mocks
        Optional<Supplier> supplierOptional = Optional.ofNullable(supplierReturnBySave);
        Mockito.when(this.supplierRepository.findById(supplierEntry.getId())).thenReturn(supplierOptional);
        Mockito.when(this.supplierRepository.save(supplierOptional.get())).thenReturn(supplierOptional.get());

        //Call
        SupplierDto supplierEffective = supplierService.confirmSupplierAccount(supplierEntry.getId());

        //Asset
        assertEquals(LocalDate.now(), supplierEffective.getValidationDate());
    }


    @Test (expected = ApplicationException.class)
    public void testConfirmSupplierThrowExceptionWhenSupplierIsNotFound() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 1L;
        SupplierDto supplierEntry = SupplierDto.builder()
                .id(supplierIdEntry)
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .password("password" + supplierIdEntry + "Test").build();

       //Call
        supplierService.confirmSupplierAccount(supplierEntry.getId());
    }


    @Test (expected = ApplicationException.class)
    public void testConfirmSupplierThrowExceptionWhenSupplierIsAlreadyValidated() throws CloneNotSupportedException {
        //Entry
        Long supplierIdEntry = 1L;
        SupplierDto supplierEntry = SupplierDto.builder()
                .id(supplierIdEntry)
                .email("emailTest@email.fr")
                .firstName("test" + supplierIdEntry)
                .lastName("TEST" + supplierIdEntry)
                .siret("0102030405")
                .webSite("https://myWebSite.com")
                .validationDate(LocalDate.now())
                .password("password" + supplierIdEntry + "Test").build();
        //Effective
        Supplier supplierReturnByEntity = this.supplierConverter.dtoToEntity(supplierEntry);
        Supplier entrySupplierSave = supplierReturnByEntity.clone();
        Supplier supplierReturnBySave = entrySupplierSave.clone();
        //Mock
        Optional<Supplier> supplierOptional = Optional.ofNullable(supplierReturnBySave);
        Mockito.when(this.supplierRepository.findById(supplierEntry.getId())).thenReturn(supplierOptional);

        //Call
        supplierService.confirmSupplierAccount(supplierEntry.getId());
    }

}
