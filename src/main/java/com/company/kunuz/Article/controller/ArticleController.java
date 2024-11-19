package com.company.kunuz.Article.controller;

import com.company.kunuz.Article.dto.ArticleDTO;
import com.company.kunuz.Article.dto.ArticleInfoDTO;
import com.company.kunuz.Article.dto.ArticleShortInfoDTO;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.service.ArticleService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO articleDTO) {
        ArticleDTO article = service.createArticle(articleDTO);
        return ResponseEntity.ok(article);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/publisher/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestHeader(value = "Accept-Post", defaultValue = "NotPublished") ArticleStatus status) {
        service.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/type/info/")
    public ResponseEntity<?> getArticleShortInfo(@RequestBody ArticleInfoDTO articleInfoDTO) {
        List<ArticleShortInfoDTO> articleDTO = service.getArticle5Info(articleInfoDTO);
        return ResponseEntity.ok(articleDTO);
    }

    // Refion id
    @GetMapping("/region/{id}")
    public ResponseEntity<?> getArticleRegion(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @PathVariable Integer id) {
        Page<ArticleShortInfoDTO> articleShortInfoDTOS = service.ByRegionId(id, page - 1, size);
        return ResponseEntity.ok(articleShortInfoDTOS);
    }

    // Category id
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getArticleCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @PathVariable Integer id) {
        Page<ArticleShortInfoDTO> articleShortInfoDTOS = service.ByCategoryId(id, page - 1, size);
        return ResponseEntity.ok(articleShortInfoDTOS);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> byId(@PathVariable("id") String id, HttpServletRequest request){
        ArticleDTO byId = service.getById(id, HeaderUtil.getUserIP(request));
        return ResponseEntity.ok().build();
    }







    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
