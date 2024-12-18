package com.company.kunuz.Article.service;

import com.company.kunuz.Article.dto.ArticleLanguageDTO;
import com.company.kunuz.Article.dto.ArticleTypeDTO;
import com.company.kunuz.Article.entity.ArticleTypeEntity;
import com.company.kunuz.Article.repository.ArticleTypeRepository;
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
        for (ArticleTypeEntity typeEntity : getAll()) {
            ArticleLanguageDTO articleLanguageDTO = new ArticleLanguageDTO();
            articleLanguageDTO.setOrder_number(typeEntity.getOrder_number());
            articleLanguageDTO.setId(typeEntity.getId());

            switch (language.toLowerCase()) {
                case "en":
                    articleLanguageDTO.setName(typeEntity.getName_en());
                    break;
                case "ru":
                    articleLanguageDTO.setName(typeEntity.getName_ru());
                    break;
                case "uz":
                default:
                    articleLanguageDTO.setName(typeEntity.getName_uz());
                    break;
            }

            languages.add(articleLanguageDTO);
        }

        return languages;

    }

}
