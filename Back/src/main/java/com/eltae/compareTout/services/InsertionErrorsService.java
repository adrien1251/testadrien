package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.InsertionErrorsConverter;
import com.eltae.compareTout.dto.InsertionErrorsDto;
import com.eltae.compareTout.repositories.InsertionErrorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class InsertionErrorsService  {

    private final InsertionErrorsRepository insertionErrorsRepository;
    private final InsertionErrorsConverter insertionErrorsConverter;


    @Autowired
    public InsertionErrorsService(InsertionErrorsRepository insertionErrorsRepository, InsertionErrorsConverter insertionErrorsConverter) {
        this.insertionErrorsRepository = insertionErrorsRepository;
        this.insertionErrorsConverter = insertionErrorsConverter;
    }

    public List<InsertionErrorsDto> getAllProductsInsertionErrors() {
        return insertionErrorsConverter.entityListToDtoList(this.insertionErrorsRepository.findAll());
    }
}
