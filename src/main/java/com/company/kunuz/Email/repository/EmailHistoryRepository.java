package com.company.kunuz.Email.repository;

import com.company.kunuz.Email.entiy.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, Integer> {

    EmailHistoryEntity findByEmail(String email);

    @Query("SELECT e FROM EmailHistoryEntity e WHERE DATE(e.createdData) = :date")
    List<EmailHistoryEntity> findAllByCreatedDate(@Param("date") LocalDateTime date);




}
