package com.eltae.compareTout.converter;
import com.eltae.compareTout.dto.CategoryDto;
import com.eltae.compareTout.dto.ShortCategoryDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class CategoryConverter extends GenericsConverter<Category, CategoryDto> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto entityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .criteriaList(category.getCriteriaList())
                .childList(category.getChildList())
                .build();
    }
    public ShortCategoryDto entityToShortDto(Category category){
        return ShortCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .criteriaList(category.getCriteriaList())
                .build();
    }

    public CategoryDto entityToDtoMinimumParams(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .build();
    }




    @Override
    public Category dtoToEntity(CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getId());
        return category.get();
    }


    public List<CategoryDto> entityListToDtoList(List<Category> entityList){
        List<CategoryDto> dtoList = new ArrayList<>();
        for(Category entity : entityList){
            dtoList.add(entityToDtoMinimumParams(entity));
        }
        return dtoList;
    }

    public List<ShortCategoryDto> entityListToShortDtoList(List<Category> entityList){
        List<ShortCategoryDto> dtoList = new ArrayList<>();
        for(Category entity : entityList){
            dtoList.add(entityToShortDto(entity));
        }
        return dtoList;
    }
}
