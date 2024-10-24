package com.company.kunuz.Profile.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.ProfileDTO;
import com.company.kunuz.Profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

//    @PostMapping("/")
//    public ResponseEntity<?> create(@RequestBody ProfileDTO profile) {
//        ProfileDTO profileDTO = service.createProfile(profile);
//        return ResponseEntity.ok(profileDTO);
//    }

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
