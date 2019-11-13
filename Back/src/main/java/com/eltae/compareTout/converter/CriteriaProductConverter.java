package com.eltae.compareTout.converter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CriteriaProductConverter extends GenericsConverter<CriteriaProduct, CriteriaProductDto> {
    @Override
    public CriteriaProductDto entityToDto(CriteriaProduct criteriaProduct) {
        return CriteriaProductDto.builder()
                .id(criteriaProduct.getCriteria().getId())
                .value(criteriaProduct.getValue())
                .criteriaName(criteriaProduct.getCriteria().getName())
                .criteriaUnit(criteriaProduct.getCriteria().getUnit())
                .type(criteriaProduct.getCriteria().getType())
                .build();
    }

    @Override
    public CriteriaProduct dtoToEntity(CriteriaProductDto criteriaProductDto) {
        return null;
    }


}
