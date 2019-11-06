package com.eltae.compareTout.converter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.repositories.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
      //          .isMandatory(criteria.isMandatory())
                .type(criteria.getType())
                .build();
    }

    @Override
    public Criteria dtoToEntity(CriteriaProductDto criteriaProductDto) {
        long idcrit=criteriaProductDto.getId();
        return  criteriaRepository.findById(idcrit);
    }


}
