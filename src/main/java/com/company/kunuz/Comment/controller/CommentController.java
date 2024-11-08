package com.company.kunuz.Comment.controller;

import com.company.kunuz.Comment.dto.CommentDTO;
import com.company.kunuz.Comment.dto.UpdateDTO;
import com.company.kunuz.Comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> update(@PathVariable Integer id , @RequestBody UpdateDTO dto){
        CommentDTO commentDTO = service.update(dto);
        return ResponseEntity.ok(commentDTO);
    }


}
