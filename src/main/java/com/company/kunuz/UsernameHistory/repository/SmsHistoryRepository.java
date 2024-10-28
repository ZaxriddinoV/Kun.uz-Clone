package com.company.kunuz.UsernameHistory.repository;

import com.company.kunuz.UsernameHistory.entiy.EmailHistoryEntity;
import com.company.kunuz.UsernameHistory.entiy.SmsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity,Integer> {
    @Query("SELECT s FROM SmsHistoryEntity s WHERE DATE(s.createdData) = :date")
    List<EmailHistoryEntity> findAllByCreatedDate(@Param("date") LocalDateTime date);

    @Query("SELECT c FROM SmsHistoryEntity c WHERE c.phone = ?1 ORDER BY c.createdData DESC")
    SmsHistoryEntity findLatestCodeByPhone(String phone);

    @Query("Select count (s) from SmsHistoryEntity s where s.phone = ?1 and s.createdData between ?2 and ?3 ")
    Long getSmsCount(String phone, LocalDateTime from, LocalDateTime to);
}
