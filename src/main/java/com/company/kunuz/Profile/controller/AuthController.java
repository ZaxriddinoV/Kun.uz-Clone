package com.company.kunuz.Profile.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.RegistrationDTO;
import com.company.kunuz.Profile.service.ProfileService;
import com.company.kunuz.UsernameHistory.dto.SmsConfirmDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private ProfileService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto){
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/confirm/{id}")
    public ResponseEntity<String> registration(@PathVariable Integer id){
        return ResponseEntity.ok(authService.registrationConfirm(id));
    }

    @PostMapping("/registration/confirm/code")
    public ResponseEntity<?> registrationConfirmCode(@RequestBody SmsConfirmDTO dto){
        authService.smsConfirm(dto);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
