package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.InsertionErrorsDto;
import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.entities.InsertionErrors;
import com.eltae.compareTout.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertionErrorsConverter extends GenericsConverter<InsertionErrors, InsertionErrorsDto> {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public InsertionErrorsDto entityToDto(InsertionErrors insertionErrors) {
        return InsertionErrorsDto.builder()
                .description(insertionErrors.getDescription())
                .id(insertionErrors.getId())
                .insertionDate(insertionErrors.getInsertionDate())
                .lineNumber(insertionErrors.getLineNumber())
                .supplier(insertionErrors.getSupplier())
                .build();
    }

    @Override
    public InsertionErrors dtoToEntity(InsertionErrorsDto insertionErrorsDto) {
        return   InsertionErrors.builder()
                .description(insertionErrorsDto.getDescription())
                .id(insertionErrorsDto.getId())
                .insertionDate(insertionErrorsDto.getInsertionDate())
                .lineNumber(insertionErrorsDto.getLineNumber())
                .supplier(insertionErrorsDto.getSupplier())
                .build();
    }




}
