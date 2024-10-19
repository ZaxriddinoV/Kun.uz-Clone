package com.company.kunuz.ArticleType.repository;

import com.company.kunuz.ArticleType.entity.ArticleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {

}
