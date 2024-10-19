package com.company.kunuz.ArticleType.controller;

import com.company.kunuz.ArticleType.dto.ArticleLanguageDTO;
import com.company.kunuz.ArticleType.dto.ArticleTypeDTO;
import com.company.kunuz.ArticleType.service.ArticleTypeService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleTypeDTO dto) {
        ArticleTypeDTO  articleTypeDTO = articleTypeService.create(dto);
        return ResponseEntity.ok(articleTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable int id,@Valid @RequestBody ArticleTypeDTO articleTypeDTO) {
        ArticleTypeDTO typeDTO = articleTypeService.apdate(id,articleTypeDTO);
        return ResponseEntity.ok(typeDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
        articleTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    private ResponseEntity<?> getAllArticle(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleTypeService.ArticleAll(page - 1, size));
    }

    @GetMapping("/{language}")
    public ResponseEntity<?> getAllArticleByLanguage(@PathVariable String language) {
        List<ArticleLanguageDTO> dtos = articleTypeService.getAllByLanguage(language);
        return ResponseEntity.ok(dtos);
    }


    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
