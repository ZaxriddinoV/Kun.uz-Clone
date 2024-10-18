package com.company.kunuz.Region.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.Service.RegionService;
import com.company.kunuz.Region.dto.RegionDTO;
import com.company.kunuz.Region.dto.RegionLanguageDTO;
import com.company.kunuz.Region.entity.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer id, @RequestBody RegionDTO region) {
        RegionDTO update = regionService.update(id, region);
        return ResponseEntity.ok(update);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllRegions() {
        List<RegionEntity>  list = regionService.getAllRegion();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{language}")
    public ResponseEntity<?> getRegionByLanguage(@PathVariable String language) {
        List<RegionLanguageDTO> languageDTO = regionService.getAllByLanguage(language);
        return ResponseEntity.ok(languageDTO);
    }





    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<?> handle(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
