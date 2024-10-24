package com.company.kunuz.Profile.service;


import com.company.kunuz.Email.service.EmailHistoryService;
import com.company.kunuz.Email.service.EmailSendingService;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.ProfileDTO;
import com.company.kunuz.Profile.dto.RegistrationDTO;
import com.company.kunuz.Profile.entity.ProfileEntity;
import com.company.kunuz.Profile.enums.ProfileStatus;
import com.company.kunuz.Profile.repository.ProfileRepository;
import com.company.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private EmailHistoryService emailHistoryService;

    /*public ProfileDTO createProfile(ProfileDTO profile) {
        boolean b = profileRepository.existsByEmail(profile.getEmail());
        if (b) {
            throw new AppBadException("Email already exists");
        }else {
            ProfileEntity save = profileRepository.save(profile.mapToEntity());
            profile.setId(save.getId());
            return profile;
        }
    }*/
    public String registration(RegistrationDTO dto) {
//        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
//        if(optional.isPresent()){
//            throw  new AppBadException("Email already exists");
//        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.IN_REGISTRATION);
        entity.setVisible(Boolean.TRUE);
        entity.setCreated_date(LocalDateTime.now());
        profileRepository.save(entity);
        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center\"> Complete Registration</h1>");
        sb.append("<br>");
        sb.append("<p>Click the link below to complete registration</p>\n");
        sb.append("<p><a style=\"padding: 5px; background-color: indianred; color: white\"  href=\"http://localhost:8080/api/auth/registration/confirm/")
                .append(entity.getId()).append("\" target=\"_blank\">Click Th</a></p>\n");


        emailSendingService.sendSimpleMessage(dto.getEmail(), "Complite Registration", sb.toString());

        emailSendingService.sendMimeMessage(dto.getEmail(), "Tasdiqlash", sb.toString());

        emailHistoryService.addEmailHistory(dto.getEmail(),"Tasdiqlash",entity.getCreated_date());
        return "Email sent";
    }

    public String registrationConfirm(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Not Completed");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.IN_REGISTRATION)) {
            throw new AppBadException("Not Completed");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Completed";
    }


    public Page<ProfileEntity> profileAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("created_date").descending());
        Page<ProfileEntity> entityList = profileRepository.findAllPage(pageRequest);
        Long total = entityList.getTotalElements();
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            dtoList.add(entity.convertToDTO());
        }
        PageImpl page1 = new PageImpl<>(dtoList, pageRequest, total);

        return page1;
    }

    public Integer deleted(Integer id) {
        return profileRepository.deleted(id);
    }
}
