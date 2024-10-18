package com.company.kunuz.Region.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.Service.RegionService;
import com.company.kunuz.Region.dto.RegionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/")
    public ResponseEntity<?> addRegion(@RequestBody RegionDTO region) {
            RegionDTO regionDTO =  regionService.created(region);
            return ResponseEntity.ok(regionDTO);
    }






    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
