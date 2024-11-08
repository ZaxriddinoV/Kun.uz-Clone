package com.company.kunuz.Article.service;

import com.company.kunuz.Article.dto.ArticleDTO;
import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.entity.ArticleTypeEntity;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.repository.ArticleRepository;
import com.company.kunuz.Article.repository.ArticleTypeRepository;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Category.repository.CategoryRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Region.entity.RegionEntity;
import com.company.kunuz.Region.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            articleEntity.setCategory_id(category.get());
            articleEntity.setImage_id(articleDTO.getImage_id());
            articleEntity.setStatus(ArticleStatus.NotPublished);
            articleEntity.setCreated_date(LocalDateTime.now());
            articleEntity.setRegion_id(region.get());
            articleEntity.setPublished_date(LocalDateTime.now());
            articleEntity.setVisible(Boolean.TRUE);
            repository.save(articleEntity);
            articleDTO.setId(articleEntity.getId());
            return articleDTO;
        }else throw new AppBadException("Category or Region not found");
    }

    public void delete(UUID id) {
        int i = repository.changeVisible(id);
        if (i == 0) throw new AppBadException("Failed to delete ");
    }


    public void changeStatus(UUID id, ArticleStatus status) {
        if (status.equals(ArticleStatus.Published)) {
            repository.changeStatusPublisher(id);
        }
        if (status.equals(ArticleStatus.NotPublished)) {
            repository.changeStatusNotPublisher(id);
        }
    }

    public List<ArticleEntity> getArticle5Info(List<Integer> id) {
        boolean b = articleTypeRepository.existsByIdIn(id);
        if (b){
            List<ArticleEntity> entityList = repository.findTop5ByArticle_type_idOrderByPublishedDateDesc(id);
            if (entityList.isEmpty()) throw new AppBadException("No articles found");
            else return entityList;
        }

        return null;
    }
}
