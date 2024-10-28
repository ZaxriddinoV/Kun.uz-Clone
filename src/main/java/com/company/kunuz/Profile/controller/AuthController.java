package com.company.kunuz.Profile.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.AuthDTO;
import com.company.kunuz.Profile.dto.RegistrationDTO;
import com.company.kunuz.Profile.service.AuthServise;
import com.company.kunuz.UsernameHistory.dto.SmsConfirmDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthServise authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto){
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/confirm/{id}")
    public ResponseEntity<String> registration(@PathVariable Integer id){
        return ResponseEntity.ok(authService.registrationConfirm(id));
    }

    @PostMapping("/registration/confirm/code")
    public ResponseEntity<?> registrationConfirmCode(@Valid @RequestBody SmsConfirmDTO dto){

        String s = authService.smsConfirm(dto, LocalDateTime.now());
        return ResponseEntity.ok().body(s);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO dto){
        authService.login(dto);
        return ResponseEntity.ok().build();
    }
    @ExceptionHandler({AppBadException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
