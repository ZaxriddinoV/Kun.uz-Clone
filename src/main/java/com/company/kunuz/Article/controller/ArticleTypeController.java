package com.company.kunuz.Article.controller;

import com.company.kunuz.Article.dto.ArticleLanguageDTO;
import com.company.kunuz.Article.dto.ArticleTypeDTO;
import com.company.kunuz.Article.service.ArticleTypeService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.JwtDTO;
import com.company.kunuz.Profile.enums.ProfileRole;
import com.company.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article-type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/")
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleTypeDTO articleTypeDTO1,
                                        @RequestHeader("Authorization") String token) {
        System.out.println(token);
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            ArticleTypeDTO articleTypeDTO = articleTypeService.create(articleTypeDTO1);
            return ResponseEntity.ok(articleTypeDTO);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable int id, @Valid @RequestBody ArticleTypeDTO articleTypeDTO,
                                           @RequestHeader("Authorization") String token ){
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            ArticleTypeDTO articleTypeDTO1 = articleTypeService.create(articleTypeDTO);
            return ResponseEntity.ok(articleTypeDTO1);
        }else {return ResponseEntity.status(403).build();}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Integer id,
                                               @RequestHeader("Authorization") String token) {
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            articleTypeService.delete(id);
            return ResponseEntity.ok().build();

        }else {return ResponseEntity.status(403).build();}
    }

    @GetMapping("/")
    private ResponseEntity<?> getAllArticle(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestHeader("Authorization") String token) {
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(articleTypeService.ArticleAll(page - 1, size));
        }else {return ResponseEntity.status(403).build();}
    }

    @GetMapping("/{language}")
    public ResponseEntity<?> getAllArticleByLanguage(@PathVariable String language) {
        List<ArticleLanguageDTO> dtos = articleTypeService.getAllByLanguage(language);
        return ResponseEntity.ok(dtos);
    }


    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
