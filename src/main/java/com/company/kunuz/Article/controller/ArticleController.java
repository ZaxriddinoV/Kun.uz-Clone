package com.company.kunuz.Article.controller;

import com.company.kunuz.Article.dto.ArticleDTO;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.service.ArticleService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid  @RequestBody ArticleDTO articleDTO){
        ArticleDTO  article = service.createArticle(articleDTO);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestHeader(value = "Accept-Post",defaultValue = "NotPublished" ) ArticleStatus status){
        service.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }





    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
