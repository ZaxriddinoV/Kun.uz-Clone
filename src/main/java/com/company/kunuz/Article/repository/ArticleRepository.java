package com.company.kunuz.Article.repository;

import com.company.kunuz.Article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET visible = false WHERE id = ?1")
    int changeVisible(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'Published' WHERE id = ?1")
    int changeStatusPublisher(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'NotPublished' WHERE id = ?1")
    int changeStatusNotPublisher(UUID id);

    @Query("FROM ArticleEntity a WHERE a.id IN :ids ORDER BY a.published_date DESC")
    List<ArticleEntity> findTop5ByArticle_type_idOrderByPublishedDateDesc(@Param("ids") List<Integer> ids);



}
