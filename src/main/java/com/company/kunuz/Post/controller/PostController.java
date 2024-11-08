package com.company.kunuz.Post.controller;

import com.company.kunuz.Post.dto.PostDTO;
import com.company.kunuz.Post.dto.PostGetDTO;
import com.company.kunuz.Post.service.PostService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO) {
        PostDTO postDTO1 = postService.create(postDTO);
        return ResponseEntity.ok(postDTO1);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        PostGetDTO byId = postService.getById(id);
        return ResponseEntity.ok(byId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PostDTO postDTO) {
        boolean  b =  postService.update(id,postDTO);
        return ResponseEntity.ok(b);
    }
}
