package com.company.kunuz.Category.controller;

import com.company.kunuz.Category.dto.CategoryDTO;
import com.company.kunuz.Category.dto.CategoryLanguageDTO;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Category.service.CategoryService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> addCategory(@Valid  @RequestBody CategoryDTO category) {
        CategoryDTO  categoryDTO = categoryService.create(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id,@Valid @RequestBody CategoryDTO category) {
        CategoryDTO categoryDTO = categoryService.apdate(id,category);
        return ResponseEntity.ok(categoryDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryEntity> list = categoryService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{language}")
    public ResponseEntity<?> getAllCategoryByLanguage(@PathVariable String language) {
        List<CategoryLanguageDTO> dtos = categoryService.getAllByLanguage(language);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/language")
    public ResponseEntity<?> getAllCategoryByLanguageHeadr(@RequestHeader(value = "Accept-Language",defaultValue = "ozbek") String language) {
        List<CategoryLanguageDTO> dtos = categoryService.getAllByLanguage(language);
        return ResponseEntity.ok(dtos);
    }

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
