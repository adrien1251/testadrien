package com.eltae.compareTout.services;



import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.converter.supplier.SupplierInscriptionConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.repositories.UserRepository;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierConverter supConv;
    private final SupplierInscriptionConverter supInsconv;
    private final UserRepository userRepository;


    public SupplierService(SupplierRepository supRep, SupplierConverter supConv, SupplierInscriptionConverter supInsconv, UserRepository userRepository){
        this.supplierRepository=supRep;
        this.supConv = supConv;
        this.supInsconv = supInsconv;
        this.userRepository = userRepository;
    }

    public List<Supplier> getAllSuppliers() {

        return this.supplierRepository.findByDiscriminatorValue("SUPPLIER");
    }
    public List<SupplierDto> getAllSuppliersNotValidate() {

        return this.supConv.entityListToDtoListSup(supplierRepository.findByValidationDateIsNull());
    }

    public boolean getSupplierWithId(Long id) {
        return this.supplierRepository.findById(id).get()==null;
    }

    public SupplierDto getSupplierInfo(Long id) {

        return this.supConv.entityToDto( this.supplierRepository.findById(id).get());
    }

    public String create(SupplierInscriptionDto supDto) {
        Supplier sup =this.supInsconv.dtoFromFrontEntity(supDto);
        this.supplierRepository.save(sup);
        return "ok";
    }
}
