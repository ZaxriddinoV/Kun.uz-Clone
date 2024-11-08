package com.company.kunuz.Post.service;

import com.company.kunuz.Attach.dto.AttachDTO;
import com.company.kunuz.Attach.service.AttachService;
import com.company.kunuz.Post.dto.PostDTO;
import com.company.kunuz.Post.entity.PostEntity;
import com.company.kunuz.Post.repository.PostAttachRepository;
import com.company.kunuz.Post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        PostEntity post = new PostEntity();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postRepository.save(post); // save
        // post attach save
//        postAttachService.create(post.getId(), postDTO.getAttachList());
        postAttachService.mere(post.getId(), postDTO.getAttachList());

        postDTO.setId(post.getId());
        return postDTO;
    }

    public PostDTO getById(Integer id) {
        PostEntity post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Post not found");
        });

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        // attach list
        List<AttachDTO> attachList = postAttachService.getAttachList(id);
        postDTO.setAttachList(attachList);
        return postDTO;
    }

    public boolean update(Integer postId, PostDTO postDTO) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> {
            throw new RuntimeException("Post not found");
        });
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postRepository.save(post); // update
        // post attach save
//        postAttachService.update(post.getId(), postDTO.getAttachList());
        postAttachService.mere(post.getId(), postDTO.getAttachList());
        return true;
    }
}
