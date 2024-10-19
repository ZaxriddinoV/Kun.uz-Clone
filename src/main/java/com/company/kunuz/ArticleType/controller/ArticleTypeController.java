package com.company.kunuz.ArticleType.controller;

import com.company.kunuz.ArticleType.dto.ArticleLanguageDTO;
import com.company.kunuz.ArticleType.dto.ArticleTypeDTO;
import com.company.kunuz.ArticleType.entity.ArticleTypeEntity;
import com.company.kunuz.ArticleType.service.ArticleTypeService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/")
    public ResponseEntity<?> addCategory(@RequestBody ArticleTypeDTO dto) {
        ArticleTypeDTO  articleTypeDTO = articleTypeService.create(dto);
        return ResponseEntity.ok(articleTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody ArticleTypeDTO articleTypeDTO) {
        ArticleTypeDTO typeDTO = articleTypeService.apdate(id,articleTypeDTO);
        return ResponseEntity.ok(typeDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        articleTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<ArticleTypeEntity> list = articleTypeService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{language}")
    public ResponseEntity<?> getAllCategoryByLanguage(@PathVariable String language) {
        List<ArticleLanguageDTO> dtos = articleTypeService.getAllByLanguage(language);
        return ResponseEntity.ok(dtos);
    }


    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
