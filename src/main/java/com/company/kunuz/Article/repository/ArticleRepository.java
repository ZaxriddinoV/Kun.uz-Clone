package com.company.kunuz.Article.repository;

import com.company.kunuz.Article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,String> {

    @Modifying
    @Transactional
    @Query("update ArticleEntity set visible = false  where id=?1")
    int changeVisible(String id);


    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'Published' WHERE id=?1")
    int changeStatusPublisher(String id);

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'NotPublished' WHERE id=?1")
    int changeStatusNotPublisher(String id);
    
}
