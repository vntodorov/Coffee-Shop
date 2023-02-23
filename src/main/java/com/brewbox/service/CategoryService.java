package com.brewbox.service;

import com.brewbox.model.entity.CategoryEntity;
import com.brewbox.model.entity.enums.CategoryEnum;
import com.brewbox.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryEntity findCategoryByName(CategoryEnum name) {
        return categoryRepository.findByName(name).orElseThrow();
    }
}
