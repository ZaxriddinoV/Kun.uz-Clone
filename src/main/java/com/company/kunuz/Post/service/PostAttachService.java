package com.company.kunuz.Post.service;

import com.company.kunuz.Attach.dto.AttachDTO;
import com.company.kunuz.Attach.entity.AttachEntity;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.Post.entity.PostAttachEntity;
import com.company.kunuz.Post.repository.PostAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostAttachService {
    @Autowired
    private PostAttachRepository postAttachRepository;
    @Autowired
    private AttachService attachService;


    public void create(Integer postId, List<String> attachId){
        for (String attachId1 : attachId){
            PostAttachEntity postAttachEntity = new PostAttachEntity();
            postAttachEntity.setPostId(postId);
            postAttachEntity.setAttachId(attachId1);
            postAttachRepository.save(postAttachEntity);
        }
    }
    public List<AttachDTO> getAttachList(Integer postId) {
        List<String> attachIdList = postAttachRepository.findAllByPostId(postId);
        List<AttachDTO> attachDTOList = new ArrayList<>();
        for (String attachId : attachIdList) {
            attachDTOList.add(attachService.getDTO(attachId));
        }
        return attachDTOList;
    }

    public void update(Integer postId, List<String> attachId){
        postAttachRepository.deleteAllById(Collections.singleton(postId));
        for (String attachId1 : attachId){
            PostAttachEntity postAttachEntity = new PostAttachEntity();
            postAttachEntity.setPostId(postId);
            postAttachEntity.setAttachId(attachId1);
            postAttachRepository.save(postAttachEntity);
        }
    }
    public void mere(Integer postId, List<AttachDTO> newAttachIdList) {
        // old [1,2,3,4]
        // new [1,7]
        // -----------
        // result [1,7]

        if (newAttachIdList == null) {
            newAttachIdList = new ArrayList<>();
        }
        List<String> oldAttachIdList = postAttachRepository.findAllByPostId(postId);
        for (String attachId : oldAttachIdList) {
            if (!exists(attachId, newAttachIdList)) {
                // delete operation {attachId}
                postAttachRepository.deleteByPostIdAndAttachId(postId, attachId);
            }
        }

        for (AttachDTO newAttach : newAttachIdList) {
            if (!oldAttachIdList.contains(newAttach.getId())) {
                // save
                PostAttachEntity entity = new PostAttachEntity();
                entity.setPostId(postId);
                entity.setAttachId(newAttach.getId());
                postAttachRepository.save(entity);
            }
        }
//        postAttachRepository.deleteByPostId(postId);
//        create(postId, newAttachIdList);
    }
    private boolean exists(String attachId, List<AttachDTO> dtoList) {
        for (AttachDTO dto : dtoList) {
            if (dto.getId().equals(attachId)) {
                return true;
            }
        }
        return false;
    }

}
