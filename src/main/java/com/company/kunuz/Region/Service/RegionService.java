package com.company.kunuz.Region.Service;

import com.company.kunuz.Region.dto.RegionDTO;
import com.company.kunuz.Region.entity.RegionEntity;
import com.company.kunuz.Region.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO created(RegionDTO region) {
        RegionEntity save = regionRepository.save(region.convertToEntity());
        region.setId(save.getId());
        return region;
    }
}
