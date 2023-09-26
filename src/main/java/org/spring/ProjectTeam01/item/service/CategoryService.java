package org.spring.ProjectTeam01.item.service;

import lombok.AllArgsConstructor;

import org.spring.ProjectTeam01.item.dto.CategoryDto;
import org.spring.ProjectTeam01.item.entity.CategoryEntity;
import org.spring.ProjectTeam01.item.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> categoryList() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDtoList.add(CategoryDto.builder()
                    .id(categoryEntity.getId())
                    .categories(categoryEntity.getCategories())
                    .build());
        }
        return categoryDtoList;
    }

    public void categoryAdd(CategoryDto categoryDto) {
        categoryRepository.save(CategoryEntity.builder()
                        .id(categoryDto.getId())
                        .categories(categoryDto.getCategories())
                        .build());
    }
}
