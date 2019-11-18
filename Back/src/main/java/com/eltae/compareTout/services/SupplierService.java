package com.eltae.compareTout.services;



import com.eltae.compareTout.converter.supplier.SupplierConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierConverter supConv;


    public SupplierService(SupplierRepository supRep, SupplierConverter supConv){
        this.supplierRepository=supRep;
        this.supConv = supConv;
    }

    public List<SupplierDto> getAllSuppliers() {

        return this.supConv.entityListToDtoListSup(supplierRepository.findAll());
    }
    public List<SupplierDto> getAllSuppliersNotValidate() {

        return this.supConv.entityListToDtoListSup(supplierRepository.findByValidationDateIsNull());
    }

    public boolean getSupplierWithId(Long id) {
        return this.supplierRepository.findById(id)==null;
    }

    public SupplierDto getSupplierInfo(Long id) {
        return this.supConv.entityToDto(this.supplierRepository.findById(id).get());
    }
}
