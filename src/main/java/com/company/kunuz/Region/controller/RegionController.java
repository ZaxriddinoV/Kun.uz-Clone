package com.company.kunuz.Region.controller;

import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.Service.RegionService;
import com.company.kunuz.Region.dto.RegionDTO;
import com.company.kunuz.Region.dto.RegionLanguageDTO;
import com.company.kunuz.Region.entity.RegionEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService regionService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> addRegion(@Valid @RequestBody RegionDTO region) {
            RegionDTO regionDTO =  regionService.created(region);
            return ResponseEntity.ok(regionDTO);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer id,@Valid @RequestBody RegionDTO region) {
        RegionDTO update = regionService.update(id, region);
        return ResponseEntity.ok(update);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
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
