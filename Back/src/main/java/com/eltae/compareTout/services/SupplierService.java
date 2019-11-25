package com.eltae.compareTout.services;



import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.converter.supplier.SupplierInscriptionConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierConverter supConv;
    private final SupplierInscriptionConverter supInsconv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SupplierService(SupplierRepository supRep, SupplierConverter supConv, SupplierInscriptionConverter supInsconv, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.supplierRepository=supRep;
        this.supConv = supConv;
        this.supInsconv = supInsconv;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Supplier> getAllSuppliers() {

        return this.supplierRepository.findByDiscriminatorValue("SUPPLIER");
    }
    public List<SupplierDto> getAllSuppliersNotValidate() {

        return this.supConv.entityListToDtoListSup(supplierRepository.findByValidationDateIsNull());
    }

    public boolean getSupplierWithId(Long id) {
              return this.supplierRepository.findByIdAndDiscriminatorValue(id,"SUPPLIER").get()==null;
    }

    public SupplierDto getSupplierInfo(Long id) {

        return this.supConv.entityToDto( this.supplierRepository.findByIdAndDiscriminatorValue(id,"SUPPLIER").get());
    }

    public Supplier getEntitySupplier(Long id){
        return this.supplierRepository.findByIdAndDiscriminatorValue(id,"SUPPLIER").get();
    }


    public Optional<Supplier> findSupplierByEmail(String email){
        return this.supplierRepository.findByEmailAndDiscriminatorValue(email,"SUPPLIER");
    }

    public String create(SupplierInscriptionDto supDto) {
        Supplier sup =this.supInsconv.dtoFromFrontEntity(supDto);
        sup.setPassword(bCryptPasswordEncoder.encode(sup.getPassword()));
        this.supplierRepository.save(sup);
        return "ok";
    }

    public String updateSupplier(SupplierDto supDto) {
        if(this.supplierRepository.findByIdAndDiscriminatorValue(supDto.getId(),"SUPPLIER")!=null) {
            Supplier supplier = this.supConv.dtoToEntity(supDto);
            this.supplierRepository.save(supplier);
            return "Supplier account has been add";
        }
        else
            return "Supplier not found";
    }


}
