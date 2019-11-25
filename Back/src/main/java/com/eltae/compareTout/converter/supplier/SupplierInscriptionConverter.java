package com.eltae.compareTout.converter.supplier;


import com.eltae.compareTout.converter.GenericsConverter;
import com.eltae.compareTout.dto.supplier.SupplierInscriptionDto;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.repositories.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class SupplierInscriptionConverter extends GenericsConverter<Supplier, SupplierInscriptionDto> {
    @Autowired
    private SupplierRepository supplierRepository;




    public SupplierInscriptionDto entityToDto(Supplier supplier) {
                return SupplierInscriptionDto.builder()
                        .email(supplier.getEmail())
                        .firstName(supplier.getFirstName())
                        .lastName(supplier.getFirstName())
                        .siret(supplier.getSiret())
                        .webSite(supplier.getWebSite())
                        .build();
    }

    @Override
    public Supplier dtoToEntity(SupplierInscriptionDto supplierInscrptionDto) {
        return null;
    }



    public List<SupplierInscriptionDto> entityListToDtoListSup(List<Supplier> all) {
        List<SupplierInscriptionDto> result=new ArrayList<>();
        for(Supplier s:all)
            result.add(entityToDto(s));
        return result;
    }
    public Supplier dtoFromFrontEntity(SupplierInscriptionDto supDto) {
        Supplier supplier = Supplier.builder()
                .firstName(supDto.getFirstName())
                .lastName(supDto.getLastName())
                .email(supDto.getEmail())
                .password(supDto.getPassword())
                .creationDate(LocalDate.now())
                .siret(supDto.getSiret())
                .webSite(supDto.getWebSite())
                .validationDate(null)
                .password(supDto.getPassword())
                .build();
        return supplier;

    }
}
