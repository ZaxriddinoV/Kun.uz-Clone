package com.company.kunuz.Profile.repository;

import com.company.kunuz.Profile.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer>, PagingAndSortingRepository<ProfileEntity,Integer> {

    boolean existsByEmail(String email);


    @Query("FROM ProfileEntity p WHERE p.visible = true ")
    Page<ProfileEntity> findAllPage(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE ProfileEntity p SET p.visible = false WHERE p.id=?1 ")
    int deleted(Integer id);
}
