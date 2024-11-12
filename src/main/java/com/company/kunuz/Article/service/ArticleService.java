package com.company.kunuz.Article.service;

import com.company.kunuz.Article.dto.ArticleDTO;
import com.company.kunuz.Article.dto.ArticleInfoDTO;
import com.company.kunuz.Article.dto.ArticleShortInfoDTO;
import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.entity.ArticleTypeEntity;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.mapper.ArticleShortInfoMapper;
import com.company.kunuz.Article.repository.ArticleRepository;
import com.company.kunuz.Article.repository.ArticleTypeRepository;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Category.repository.CategoryRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.entity.RegionEntity;
import com.company.kunuz.Region.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private final AttachService attachService;

    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        List<ArticleTypeEntity> articleTypeEntityList = new ArrayList<>();
        for (Integer article : articleDTO.getArticleType()){
            Optional<ArticleTypeEntity> byId = articleTypeRepository.findById(article);
            byId.ifPresent(articleTypeEntityList::add);
        }
        Optional<CategoryEntity> category = categoryRepository.findById(articleDTO.getCategory_id());
        Optional<RegionEntity> region = regionRepository.findById(articleDTO.getRegion_id());
        if (category.isPresent() && region.isPresent()) {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setTitle(articleDTO.getTitle());
            articleEntity.setContent(articleDTO.getContent());
            articleEntity.setArticle_type_id(articleTypeEntityList);
            articleEntity.setDescription(articleDTO.getDescription());
            articleEntity.setCategoryId(category.get().getId());
            articleEntity.setImageId(articleDTO.getImage_id());
            articleEntity.setStatus(ArticleStatus.NotPublished);
            articleEntity.setCreated_date(LocalDateTime.now());
            articleEntity.setRegionId(region.get().getId());
            articleEntity.setPublished_date(LocalDateTime.now());
            articleEntity.setVisible(Boolean.TRUE);
            repository.save(articleEntity);
            articleDTO.setId(articleEntity.getId());
            return articleDTO;
        }else throw new AppBadException("Category or Region not found");
    }

    public void delete(String id) {
        int i = repository.changeVisible(id);
        if (i == 0) throw new AppBadException("Failed to delete ");
    }


    public void changeStatus(String id, ArticleStatus status) {
        if (status.equals(ArticleStatus.Published)) {
            repository.changeStatusPublisher(id);
        }
        if (status.equals(ArticleStatus.NotPublished)) {
            repository.changeStatusNotPublisher(id);
        }
    }

    public List<ArticleShortInfoDTO> getArticle5Info(ArticleInfoDTO dto) {
        if (dto.getType() == null){
            List<ArticleShortInfoMapper> mapperList = repository.getLastIdNotIn(ArticleStatus.Published,
                    dto.getIds(),
                    PageRequest.of(0, dto.getNumber()));

            return mapperList.stream().map(item -> toShortInfo(item)).toList();
        }
        boolean b = articleTypeRepository.existsById(dto.getType());
        if (b) {
            List<ArticleShortInfoDTO> ArticleShortInfoDTO = new ArrayList<>();
            List<ArticleEntity> entityList = repository.findArticlesExcludingIds(dto.getIds(),dto.getType(),PageRequest.of(0, dto.getNumber()));
            for (ArticleEntity entity : entityList) {
                ArticleShortInfoDTO articleShortInfoDTO = new ArticleShortInfoDTO();
                articleShortInfoDTO.setId(entity.getId());
                articleShortInfoDTO.setTitle(entity.getTitle());
                articleShortInfoDTO.setDescription(entity.getDescription());
                articleShortInfoDTO.setImage(entity.getImageId());
                articleShortInfoDTO.setPublishedDate(entity.getPublished_date());
                ArticleShortInfoDTO.add(articleShortInfoDTO);
            }
            return ArticleShortInfoDTO;
        }
        return null;
    }

    public ArticleEntity getByArticle(String articleId) {
        Optional<ArticleEntity> byId = repository.findById(articleId);
        if (byId.isPresent()) {
            return byId.get();
        }else return null;
    }
    public List<ArticleShortInfoDTO> getLast8(List<String> excludeIdList) {
        List<ArticleShortInfoMapper> mapperList = repository.getLastIdNotIn(ArticleStatus.Published,
                excludeIdList,
                PageRequest.of(0, 8));
        return mapperList.stream().map(item -> toShortInfo(item)).toList();
    }

    public ArticleShortInfoDTO toShortInfo(ArticleShortInfoMapper mapper) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishDate());
        dto.setImage(mapper.getImageId());
        return dto;
    }


    public Page<ArticleShortInfoDTO> ByRegionId(Integer id,int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("created_date").descending());

        Page<ArticleEntity> entityList = repository.getAll5(id,pageRequest);
        Long total = entityList.getTotalElements();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        PageImpl page1 = new PageImpl<>(dtoList, pageRequest, total);

        return page1;
    }

    public Page<ArticleShortInfoDTO> ByCategoryId(Integer id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("created_date").descending());

        Page<ArticleEntity> entityList = repository.getAll6(id,pageRequest);
        Long total = entityList.getTotalElements();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        PageImpl page1 = new PageImpl<>(dtoList, pageRequest, total);

        return page1;
    }
    private ArticleShortInfoDTO toDTO(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImageId());
        dto.setPublishedDate(entity.getPublished_date());
        return dto;
    }

}
