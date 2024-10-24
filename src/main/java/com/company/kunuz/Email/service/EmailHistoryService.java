package com.company.kunuz.Email.service;

import com.company.kunuz.Email.entiy.EmailHistoryEntity;
import com.company.kunuz.Email.repository.EmailHistoryRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public void addEmailHistory(String email, String message, LocalDateTime createdDate) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        entity.setCreatedData(createdDate);
        emailHistoryRepository.save(entity);
    }

    public EmailHistoryEntity getByEmail(String email) {
        EmailHistoryEntity byEmail = emailHistoryRepository.findByEmail(email);
        if (byEmail == null) {
            throw new AppBadException("Email not found");
        }
        return byEmail;
    }
    public List<EmailHistoryEntity> getAllGiven(LocalDate date) {
        LocalDateTime now = date.atStartOfDay();
        List<EmailHistoryEntity> allByCreatedDate = emailHistoryRepository.findAllByCreatedDate(now);
        if (allByCreatedDate.isEmpty()) {
            throw new AppBadException("No email history found");
        }
        return allByCreatedDate;

    }

}
