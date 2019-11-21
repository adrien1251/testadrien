package com.eltae.compareTout.converter;
import com.eltae.compareTout.dto.criteria.CriteriaDto;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CriteriaConverter extends GenericsConverter<Criteria, CriteriaProductDto> {
    @Autowired
    private CriteriaRepository criteriaRepository;


    @Override
    public CriteriaProductDto entityToDto(Criteria criteria) {
        return CriteriaProductDto.builder()
                .id(criteria.getId())
                .criteriaName(criteria.getName())
                .criteriaUnit(criteria.getUnit())
                .type(criteria.getType())
                .build();
    }
    public CriteriaDto entityToDtoWithMandatory(Criteria criteria, Category id_cat) {
        return CriteriaDto.builder()
                .id(criteria.getId())
                .name(criteria.getName())
                .unit(criteria.getUnit())
                .type(criteria.getType())
                .isMandatory(id_cat.isMandatory(criteria.getId()))
                .build();
    }
    public List<CriteriaDto> entityListToDtoListWithMandatory(List<Criteria> entityList,Category id_cat){
        List<CriteriaDto> dtoList = new ArrayList<>();
        for(Criteria entity : entityList){
            dtoList.add(entityToDtoWithMandatory(entity,id_cat));
        }
        return dtoList;
    }
    @Override
    public Criteria dtoToEntity(CriteriaProductDto criteriaProductDto) {
        return  criteriaRepository
                .findById(criteriaProductDto.getId())
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Criteria " + criteriaProductDto.getId() + " not found"));
    }


}
