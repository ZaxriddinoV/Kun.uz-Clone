package com.company.kunuz.Category.service;

import com.company.kunuz.Category.dto.CategoryDTO;
import com.company.kunuz.Category.dto.CategoryLanguageDTO;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Category.repository.CategoryRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity getById(Integer id) {
        Optional<CategoryEntity> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getVisible().equals(true)) {
                return byId.get();
            } else throw new AppBadException("This Category is Inactive (delete)");
        } else throw new AppBadException("This Category does not exist");
    }

    public CategoryDTO create(CategoryDTO category) {
        CategoryEntity save = categoryRepository.save(category.convertToEntity());
        category.setId(save.getId());
        return category;
    }

    public CategoryDTO apdate(Integer id, CategoryDTO category) {
        CategoryEntity byId = getById(id);
        byId.setName_uz(category.getName_uz());
        byId.setName_ru(category.getName_ru());
        byId.setName_en(category.getName_en());
        byId.setOrder_number(category.getOrder_number());
        categoryRepository.save(byId);
        category.setId(id);
        return category;
    }

    public void delete(Integer id) {
        CategoryEntity byId = getById(id);
        byId.setVisible(false);
        categoryRepository.save(byId);
    }

    public List<CategoryEntity> getAll() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryEntity> result = new ArrayList<CategoryEntity>();
        for (CategoryEntity category : all) {
            if (category.getVisible().equals(true)) {
                result.add(category);
            }
        }
        return result;
    }

    public List<CategoryLanguageDTO> getAllByLanguage(String language) {
        List<CategoryLanguageDTO> languages = new ArrayList<>();
        switch (language) {
            case "english":
                for (CategoryEntity category : getAll()) {
                    CategoryLanguageDTO categoryLanguageDTO = new CategoryLanguageDTO();
                    categoryLanguageDTO.setOrder_number(category.getOrder_number());
                    categoryLanguageDTO.setName(category.getName_en());
                    categoryLanguageDTO.setId(category.getId());
                    languages.add(categoryLanguageDTO);
                }
                return languages;
            case "russia":
                for (CategoryEntity category : getAll()) {
                    CategoryLanguageDTO categoryLanguageDTO = new CategoryLanguageDTO();
                    categoryLanguageDTO.setOrder_number(category.getOrder_number());
                    categoryLanguageDTO.setName(category.getName_ru());
                    categoryLanguageDTO.setId(category.getId());
                    languages.add(categoryLanguageDTO);
                }
                return languages;
            case "ozbek":
                for (CategoryEntity category : getAll()) {
                    CategoryLanguageDTO categoryLanguageDTO = new CategoryLanguageDTO();
                    categoryLanguageDTO.setOrder_number(category.getOrder_number());
                    categoryLanguageDTO.setName(category.getName_uz());
                    categoryLanguageDTO.setId(category.getId());
                    languages.add(categoryLanguageDTO);
                }
                return languages;
            default:
                for (CategoryEntity category : getAll()) {
                    CategoryLanguageDTO categoryLanguageDTO = new CategoryLanguageDTO();
                    categoryLanguageDTO.setOrder_number(category.getOrder_number());
                    categoryLanguageDTO.setName(category.getName_uz());
                    categoryLanguageDTO.setId(category.getId());
                    languages.add(categoryLanguageDTO);
                }
                return languages;
        }
    }

}
