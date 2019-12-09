package com.eltae.compareTout.services;



import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.converter.supplier.SupplierInscriptionConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierConverter supplierConverter;
    private final SupplierConverter supConv;
    private final SupplierInscriptionConverter supInsconv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SupplierService(
            SupplierRepository supRep,
            SupplierConverter supplierConverter,
            SupplierConverter supConv,
            SupplierInscriptionConverter supInsconv,
            BCryptPasswordEncoder bCryptPasswordEncoder){
        this.supplierRepository=supRep;
        this.supplierConverter = supplierConverter;
        this.supConv = supConv;
        this.supInsconv = supInsconv;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Supplier> getAllSuppliers() {
        return this.supplierRepository.findByDiscriminatorValue("SUPPLIER");
    }

    public List<SupplierDto> getAllSuppliersNotValidate() {
        return this.supConv.entityListToDtoListSup(supplierRepository.findAllByValidationDateIsNull());
    }

    public boolean supplierExists(Long id) {
        return this.supplierRepository
                .findByIdAndDiscriminatorValue(id, "SUPPLIER").isPresent();
    }

    public SupplierDto getSupplierInfo(Long id) {
        return supConv.entityToDto(supplierRepository
                .findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Invalid supplier ID")));
    }


    public Supplier getEntitySupplier(Long id) {
        if(supplierExists(id))
        return this.supplierRepository.findByIdAndDiscriminatorValue(id, "SUPPLIER").get();
        else
            throw new ApplicationException(HttpStatus.NOT_FOUND, "invalid supplier ID");

    }


    public Optional<Supplier> findSupplierByEmail(String email){
        return this.supplierRepository.findByEmailAndDiscriminatorValue(email,"SUPPLIER");
    }

    public SupplierDto create(SupplierInscriptionDto supDto) {
        Supplier sup =this.supInsconv.dtoFromFrontEntity(supDto);
        if(findSupplierByEmail(sup.getEmail()).isPresent()){
            throw new ApplicationException(HttpStatus.CONFLICT, "This email already exist");
        }
        sup.setPassword(bCryptPasswordEncoder.encode(sup.getPassword()));
        return this.supplierConverter.entityToDto(this.supplierRepository.save(sup));
    }

    public SupplierDto updateSupplier(SupplierDto supDto) {
        if (supplierExists(supDto.getId())) {
            Supplier supplier = this.supConv.dtoToEntity(supDto);
            return supplierConverter.entityToDto(this.supplierRepository.save(supplier));
        } else
            throw new ApplicationException(HttpStatus.NOT_FOUND, "invalid supplier ID");
    }

    public SupplierDto confirmSupplierAccount(long supplierId) {
        Supplier supplier = supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.resolve(400), "invalid supplier ID"));
        if (supplier.getValidationDate() != null)
            throw new ApplicationException(HttpStatus.PRECONDITION_FAILED, "Already validated supplier");
        supplier.setValidationDate(LocalDate.now());
        return supConv.entityToDto(supplierRepository.save(supplier));
    }

}
