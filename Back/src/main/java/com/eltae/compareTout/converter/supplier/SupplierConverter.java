package com.eltae.compareTout.converter.supplier;

import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SupplierConverter extends GenericsConverter<Supplier, SupplierDto> {
    @Autowired
    private SupplierRepository supplierRepository;




    public SupplierDto entityToDto(Supplier supplier) {
                return SupplierDto.builder()
                        .id(supplier.getId())
                        .email(supplier.getEmail())
                        .firstName(supplier.getFirstName())
                        .lastName(supplier.getFirstName())
                        .siret(supplier.getSiret())
                        .webSite(supplier.getWebSite())
                        .creationDate(supplier.getCreationDate())
                        .validationDate(supplier.getValidationDate())
                        .build();
    }

    @Override
    public Supplier dtoToEntity(SupplierDto supplierDto) {
        return this.supplierRepository.findById(supplierDto.getId()).get();
    }


    public List<SupplierDto> entityListToDtoListSup(List<Supplier> all) {
        List<SupplierDto> result=new ArrayList<>();
        for(Supplier s:all)
            result.add(entityToDto(s));
        return result;
    }
}
