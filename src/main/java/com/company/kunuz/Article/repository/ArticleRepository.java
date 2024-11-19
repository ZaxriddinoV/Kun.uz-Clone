package com.company.kunuz.Article.repository;

import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Article.mapper.ArticleShortInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,String>, PagingAndSortingRepository<ArticleEntity,String> {

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET visible = false WHERE id = ?1")
    int changeVisible(String id);

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'Published' WHERE id = ?1")
    int changeStatusPublisher(String id);

    @Modifying
    @Transactional
    @Query("UPDATE ArticleEntity SET status = 'NotPublished' WHERE id = ?1")
    int changeStatusNotPublisher(String id);

    @Query("FROM ArticleEntity a WHERE a.id IN :ids ORDER BY a.published_date DESC")
    List<ArticleEntity> findTop5ByArticle_type_idOrderByPublishedDateDesc(@Param("ids") List<Integer> ids);

//    @Query("FROM ArticleEntity a WHERE a.id NOT IN :ids AND a.article_type_id = :articleTypeId ORDER BY a.published_date DESC")
//    List<ArticleEntity> findArticlesExcludingIds(@Param("ids") List<String> ids, @Param("articleTypeId") Integer articleTypeId, Pageable pageable);


    @Query(" select a.id as id,  a.title as title, a.description as description, a.imageId as imageId, a.published_date as publishedDate " +
            "  From ArticleEntity a  where  a.id not in ?2 and a.status =?1 and a.visible = true order by a.created_date desc")
    List<ArticleShortInfoMapper> getLastIdNotIn(ArticleStatus status,
                                                List<String> excludeIdList, Pageable pageable);


    @Query("FROM ArticleEntity a where a.categoryId = ?1 ")
    Page<ArticleEntity> getAll6(Integer id,PageRequest pageRequest);

    @Query("FROM ArticleEntity a where a.regionId = ?1 ")
    Page<ArticleEntity> getAll5(Integer id, PageRequest pageRequest);
}
