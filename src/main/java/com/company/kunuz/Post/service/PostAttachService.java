package com.company.kunuz.Post.service;

import com.company.kunuz.Attach.dto.AttachDTO;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.Post.entity.PostAttachEntity;
import com.company.kunuz.Post.repository.PostAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostAttachService {
    @Autowired
    private PostAttachRepository postAttachRepository;
    @Autowired
    private AttachService attachService;


    public void create(Integer postId, List<AttachDTO> attachIdList) {
        if (attachIdList == null) {
            return;
        }
        for (AttachDTO dto : attachIdList) {
            PostAttachEntity entity = new PostAttachEntity();
            entity.setPostId(postId);
            entity.setAttachId(dto.getId());
            postAttachRepository.save(entity);
        }
    }

    public void update(Integer postId, List<AttachDTO> newAttachIdList) {
        // old [1,2,3,4]
        // new [1,7]
        // -----------
        // result [1,7]
        postAttachRepository.deleteByPostId(postId);
        create(postId, newAttachIdList);
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


    public List<AttachDTO> getAttachList(Integer postId) {
        List<String> attachIdList = postAttachRepository.findAllByPostId(postId);
        List<AttachDTO> attachDTOList = new ArrayList<>();
        for (String attachId : attachIdList) {
            attachDTOList.add(attachService.getDTO(attachId));
        }
        return attachDTOList;
    }

}
