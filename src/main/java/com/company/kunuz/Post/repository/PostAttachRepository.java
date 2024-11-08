package com.company.kunuz.Post.repository;

import com.company.kunuz.Post.entity.PostAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostAttachRepository extends JpaRepository<PostAttachEntity,Integer> {

    List<PostAttachEntity> findByPostId(Integer postId);

    List<String> findAllByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttachEntity where postId =?1")
    void deleteByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttachEntity where postId =?1 and attachId = ?2")
    void deleteByPostIdAndAttachId(Integer postId, String attachId);

}
