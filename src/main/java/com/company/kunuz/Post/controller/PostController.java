package com.company.kunuz.Post.controller;

import com.company.kunuz.Post.dto.PostDTO;
import com.company.kunuz.Post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO) {
        PostDTO result = postService.create(postDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public PostDTO byId(@PathVariable("id") Integer id) {
        return postService.getById(id);
    }

    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Integer id,
                          @RequestBody PostDTO postDTO) {
        return postService.update(id, postDTO);
    }
}
