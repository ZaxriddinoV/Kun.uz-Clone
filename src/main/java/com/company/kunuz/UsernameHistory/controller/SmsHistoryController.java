package com.company.kunuz.UsernameHistory.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.UsernameHistory.entiy.EmailHistoryEntity;
import com.company.kunuz.UsernameHistory.entiy.SmsHistoryEntity;
import com.company.kunuz.UsernameHistory.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sms-history")
public class SmsHistoryController {
    @Autowired
    SmsHistoryService smsHistoryService;

//    @GetMapping("/phone/{number}")
//    public ResponseEntity<?> getEmailHistoryService(@RequestParam(value = "number") String phone) {
//        List<SmsHistoryEntity> entity =  smsHistoryService.;
//        return ResponseEntity.ok(entity);
//    }

//    @GetMapping("/date/{date}")
//    public ResponseEntity<?> getEmailHistoryService(@RequestParam LocalDate date) {
//        return ResponseEntity.ok(smsHistoryService.getAllGiven(date));
//    }

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
