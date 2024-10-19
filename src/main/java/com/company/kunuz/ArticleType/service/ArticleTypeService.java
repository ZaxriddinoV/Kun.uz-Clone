package com.company.kunuz.ArticleType.service;

import com.company.kunuz.ArticleType.dto.ArticleLanguageDTO;
import com.company.kunuz.ArticleType.dto.ArticleTypeDTO;
import com.company.kunuz.ArticleType.entity.ArticleTypeEntity;
import com.company.kunuz.ArticleType.repository.ArticleTypeRepository;
import com.company.kunuz.Category.dto.CategoryDTO;
import com.company.kunuz.Category.dto.CategoryLanguageDTO;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeEntity getById(Integer id) {
        Optional<ArticleTypeEntity> byId = articleTypeRepository.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getVisible().equals(true)) {
                return byId.get();
            } else throw new AppBadException("This Article is Inactive (delete)");
        } else throw new AppBadException("This Article does not exist");
    }

    public ArticleTypeDTO create(ArticleTypeDTO articleTypeDTO) {
        ArticleTypeEntity save = articleTypeRepository.save(articleTypeDTO.convertToEntity());
        articleTypeDTO.setId(save.getId());
        return articleTypeDTO;
    }

    public Page<ArticleTypeDTO> ArticleAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<ArticleTypeEntity> entityList = articleTypeRepository.findByAll(pageRequest);
        Long total = entityList.getTotalElements();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setId(entity.getId());
            dto.setOrder_number(entity.getOrder_number());
            dto.setName_uz(entity.getName_uz());
            dto.setName_en(entity.getName_en());
            dto.setName_ru(entity.getName_ru());
            dtoList.add(dto);
        }
        PageImpl page1 = new PageImpl<>(dtoList, pageRequest, total);

        return page1;
    }

    public ArticleTypeDTO apdate(Integer id, ArticleTypeDTO articleTypeDTO) {
        ArticleTypeEntity byId = getById(id);
        byId.setName_uz(articleTypeDTO.getName_uz());
        byId.setName_ru(articleTypeDTO.getName_ru());
        byId.setName_en(articleTypeDTO.getName_en());
        byId.setOrder_number(articleTypeDTO.getOrder_number());
        articleTypeRepository.save(byId);
        articleTypeDTO.setId(id);
        return articleTypeDTO;
    }

    public void delete(Integer id) {
        ArticleTypeEntity byId = getById(id);
        byId.setVisible(false);
        articleTypeRepository.save(byId);
    }

    public List<ArticleTypeEntity> getAll() {
        List<ArticleTypeEntity> all = articleTypeRepository.findAll();
        List<ArticleTypeEntity> result = new ArrayList<>();
        for (ArticleTypeEntity entity : all) {
            if (entity.getVisible().equals(true)) {
                result.add(entity);
            }
        }
        return result;
    }

    public List<ArticleLanguageDTO> getAllByLanguage(String language) {
        List<ArticleLanguageDTO> languages = new ArrayList<>();
        switch (language) {
            case "english":
                for (ArticleTypeEntity typeEntity : getAll()) {
                    ArticleLanguageDTO articleLanguageDTO = new ArticleLanguageDTO();
                    articleLanguageDTO.setOrder_number(typeEntity.getOrder_number());
                    articleLanguageDTO.setName(typeEntity.getName_en());
                    articleLanguageDTO.setId(typeEntity.getId());
                    languages.add(articleLanguageDTO);
                }
                return languages;
            case "russia":
                for (ArticleTypeEntity typeEntity : getAll()) {
                    ArticleLanguageDTO articleLanguageDTO = new ArticleLanguageDTO();
                    articleLanguageDTO.setOrder_number(typeEntity.getOrder_number());
                    articleLanguageDTO.setName(typeEntity.getName_ru());
                    articleLanguageDTO.setId(typeEntity.getId());
                    languages.add(articleLanguageDTO);
                }
                return languages;
            case "ozbek":
                for (ArticleTypeEntity articleType : getAll()) {
                    ArticleLanguageDTO articleLanguageDTO = new ArticleLanguageDTO();
                    articleLanguageDTO.setOrder_number(articleType.getOrder_number());
                    articleLanguageDTO.setName(articleType.getName_uz());
                    articleLanguageDTO.setId(articleType.getId());
                    languages.add(articleLanguageDTO);
                }
                return languages;
            default:
                for (ArticleTypeEntity articleType : getAll()) {
                    ArticleLanguageDTO articleLanguageDTO = new ArticleLanguageDTO();
                    articleLanguageDTO.setOrder_number(articleType.getOrder_number());
                    articleLanguageDTO.setName(articleType.getName_uz());
                    articleLanguageDTO.setId(articleType.getId());
                    languages.add(articleLanguageDTO);
                }
                return languages;
        }
    }

}
