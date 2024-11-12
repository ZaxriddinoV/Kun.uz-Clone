package com.company.kunuz.Post.repository;

import com.company.kunuz.Post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
