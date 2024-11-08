package com.company.kunuz.Article.controller;

import com.company.kunuz.Article.dto.ArticleDTO;
import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.service.ArticleService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid  @RequestBody ArticleDTO articleDTO){
        ArticleDTO  article = service.createArticle(articleDTO);
        return ResponseEntity.ok(article);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/publisher/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestHeader(value = "Accept-Post",defaultValue = "NotPublished" ) ArticleStatus status){
        service.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/type/info/")
    public ResponseEntity<?> getArticleShortInfo(@RequestBody List<Integer> type){
        List<ArticleEntity> articleDTO =   service.getArticle5Info(type);
        return ResponseEntity.ok(articleDTO);
    }





    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
