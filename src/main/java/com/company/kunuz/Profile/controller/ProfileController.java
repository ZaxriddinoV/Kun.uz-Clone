package com.company.kunuz.Profile.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.JwtDTO;
import com.company.kunuz.Profile.dto.ProfileDTO;
import com.company.kunuz.Profile.dto.UpdateProfileDetailDTO;
import com.company.kunuz.Profile.enums.ProfileRole;
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
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(201).body(service.createProfile(requestDTO));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProfile(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @RequestHeader("Authorization") String token) {
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(service.profileAll(page - 1, size));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("id") Integer id,
                                           @RequestHeader("Authorization") String token) {
        JwtDTO dto = JwtUtil.decode(token.substring(7));
        if (dto.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok().body(service.deleted(id));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody @Valid UpdateProfileDetailDTO requestDTO) {
        return ResponseEntity.ok().body(service.updateDetail(requestDTO));
    }


    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
