package com.company.kunuz.Comment.controller;

import com.company.kunuz.Comment.dto.CommentAllDTO;
import com.company.kunuz.Comment.dto.CommentDTO;
import com.company.kunuz.Comment.dto.UpdateDTO;
import com.company.kunuz.Comment.entity.CommentEntity;
import com.company.kunuz.Comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    @PostMapping("/")
    public ResponseEntity<?> createComment(@Valid @RequestBody  CommentDTO comment,
                                           @RequestHeader("Authorization") String token) {
        CommentDTO createDTO = service.create(comment,token);
        return ResponseEntity.ok(createDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id , @RequestBody UpdateDTO dto,
                                    @RequestHeader("Authorization") String token){
        CommentDTO commentDTO = service.update(id,dto,token);
        return ResponseEntity.ok(commentDTO);
    }
//  Get Article Comment List By Article Id
    @GetMapping("/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable UUID id){
        List<CommentDTO> commentDTOS = service.getArticleId(id);
        return ResponseEntity.ok(commentDTOS);
    }
//  Comment List (pagination) (ADMIN)

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getComments(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size){
        Page<CommentAllDTO> dtoPage = service.getAllComment(page - 1,size);
        return ResponseEntity.ok(dtoPage);
    }


}
