package com.company.kunuz.Profile.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.JwtDTO;
import com.company.kunuz.Profile.dto.ProfileDTO;
import com.company.kunuz.Profile.service.ProfileService;
import com.company.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @PostMapping("/")
    public ResponseEntity<ProfileDTO> addProfile(@RequestBody @Valid ProfileDTO requestDTO,
                                                 @RequestHeader("Authorization") String token) {
        System.out.println(token);
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        return ResponseEntity.status(201).body(service.createProfile(requestDTO));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProfile(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.profileAll(page - 1, size));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(service.deleted(id));
    }

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
