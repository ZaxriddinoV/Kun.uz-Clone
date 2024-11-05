package com.company.kunuz.Attach.repository;

import com.company.kunuz.Attach.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface AttachRepository extends JpaRepository<AttachEntity,String> , PagingAndSortingRepository<AttachEntity,String> {


    @Query("FROM AttachEntity a ")
    Page<AttachEntity> allAttach(Pageable pageable);

}
