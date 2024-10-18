package com.company.kunuz.Region.Service;

import com.company.kunuz.Category.dto.CategoryLanguageDTO;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.dto.RegionDTO;
import com.company.kunuz.Region.dto.RegionLanguageDTO;
import com.company.kunuz.Region.entity.RegionEntity;
import com.company.kunuz.Region.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public RegionEntity getById(Integer id) {
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getVisible().equals(true)) {
                return byId.get();
            }else throw new AppBadException("This Region is inactive");
        }else throw new AppBadException("This Region does not exist");
    }

    public RegionDTO created(RegionDTO region) {
        RegionEntity save = regionRepository.save(region.convertToEntity());
        region.setId(save.getId());
        return region;
    }

    public RegionDTO update(Integer id, RegionDTO region){
        RegionEntity byId = getById(id);
        byId.setName_uz(region.getName_uz());
        byId.setName_ru(region.getName_ru());
        byId.setName_en(region.getName_en());
        regionRepository.save(byId);
        region.setId(id);
        return region;
    }
    public void delete(Integer id) {
        RegionEntity byId = getById(id);
        regionRepository.save(byId);
        byId.setVisible(false);
    }

    public List<RegionEntity> getAllRegion() {
        List<RegionEntity> all = regionRepository.findAll();
        List<RegionEntity> result = new ArrayList<>();
        for (RegionEntity regionEntity : all) {
            if (regionEntity.getVisible().equals(true)) {
                result.add(regionEntity);
            }
        }
        return result;
    }

    public List<RegionLanguageDTO> getAllByLanguage(String language) {
        List<RegionLanguageDTO> languages = new ArrayList<>();
        switch (language) {
            case "english":
                for (RegionEntity category : getAllRegion()) {
                    RegionLanguageDTO regionLanguageDTO = new RegionLanguageDTO();
                    regionLanguageDTO.setOrder_number(category.getOrder_number());
                    regionLanguageDTO.setName(category.getName_en());
                    regionLanguageDTO.setId(category.getId());
                    languages.add(regionLanguageDTO);
                }
                return languages;
            case "russia":
                for (RegionEntity category : getAllRegion()) {
                    RegionLanguageDTO languageDTO = new RegionLanguageDTO();
                    languageDTO.setOrder_number(category.getOrder_number());
                    languageDTO.setName(category.getName_ru());
                    languageDTO.setId(category.getId());
                    languages.add(languageDTO);
                }
                return languages;
            case "ozbek":
                for (RegionEntity category : getAllRegion()) {
                    RegionLanguageDTO languageDTO = new RegionLanguageDTO();
                    languageDTO.setOrder_number(category.getOrder_number());
                    languageDTO.setName(category.getName_uz());
                    languageDTO.setId(category.getId());
                    languages.add(languageDTO);
                }
                return languages;
            default:
                for (RegionEntity category : getAllRegion()) {
                    RegionLanguageDTO languageDTO = new RegionLanguageDTO();
                    languageDTO.setOrder_number(category.getOrder_number());
                    languageDTO.setName(category.getName_uz());
                    languageDTO.setId(category.getId());
                    languages.add(languageDTO);
                }
                return languages;
        }
    }
}
