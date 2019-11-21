package com.eltae.compareTout.converter;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.entities.CriteriaProduct;
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
