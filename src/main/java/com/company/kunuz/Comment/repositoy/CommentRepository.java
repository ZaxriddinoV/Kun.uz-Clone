package com.company.kunuz.Comment.repositoy;

import com.company.kunuz.Comment.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer> {

    List<CommentEntity> findAllByArticleId(String id);

    @Query("FROM CommentEntity s")
    Page<CommentEntity> getAll(PageRequest pageRequest);
}
