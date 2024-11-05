package com.company.kunuz.Attach.Controller;

import com.company.kunuz.Attach.dto.AttachDTO;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO upload = attachService.upload(file);
        return ResponseEntity.ok(upload);
    }
    @GetMapping("/open/{fileName}")
    public ResponseEntity<Resource> open(@PathVariable String fileName) {
        return attachService.open(fileName);
    }

    

    @ExceptionHandler({AppBadException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
