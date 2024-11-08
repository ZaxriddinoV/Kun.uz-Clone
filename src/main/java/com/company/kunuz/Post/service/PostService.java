package com.company.kunuz.Post.service;

import com.company.kunuz.Attach.entity.AttachEntity;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.Post.entity.PostAttachEntity;
import com.company.kunuz.Post.dto.PostDTO;
import com.company.kunuz.Post.dto.PostGetDTO;
import com.company.kunuz.Post.entity.PostEntity;
import com.company.kunuz.Post.repository.PostAttachRepository;
import com.company.kunuz.Post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostAttachService postAttachService;
    @Autowired
    AttachService attachService;
    @Autowired
    PostAttachRepository postAttachRepository;

    public PostDTO create(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postRepository.save(postEntity);
        postAttachService.create(postEntity.getId(),postDTO.getImageIdList());
        postDTO.setId(postEntity.getId());
        return postDTO;
    }

    public PostGetDTO getById(Integer id) {
        Optional<PostEntity> byId = postRepository.findById(id);
        List<AttachEntity> attachEntities = new ArrayList<>();
        if (byId.isPresent()) {
            PostEntity postEntity = byId.get();
            List<PostAttachEntity> byPostId = postAttachRepository.findByPostId(id);
            for (PostAttachEntity list :byPostId) {
                AttachEntity entity = attachService.getEntity(list.getAttachId());
                attachEntities.add(entity);
            }
            PostGetDTO postGetDTO = new PostGetDTO();
            postGetDTO.setTitle(postEntity.getTitle());
            postGetDTO.setContent(postEntity.getContent());
            postGetDTO.setId(postEntity.getId());
            postGetDTO.setImageIdList(attachEntities);
            return postGetDTO;
        }
        return null;
    }

    public boolean update(Integer id, PostDTO postDTO) {
        PostEntity postEntity = postRepository.findById(id).get();
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postRepository.save(postEntity);
        postAttachService.update(id,postDTO.getImageIdList());
        return true;
    }
}
