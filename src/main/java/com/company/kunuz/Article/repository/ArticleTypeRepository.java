package com.company.kunuz.Article.repository;

import com.company.kunuz.Article.entity.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer>, PagingAndSortingRepository<ArticleTypeEntity,Integer> {

    @Query("FROM ArticleTypeEntity s")
    Page<ArticleTypeEntity> findByAll(Pageable pageable);

}
