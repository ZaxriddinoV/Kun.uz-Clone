package com.company.kunuz.Comment.service;

import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.service.ArticleService;
import com.company.kunuz.Comment.dto.CommentAllDTO;
import com.company.kunuz.Comment.dto.CommentDTO;
import com.company.kunuz.Comment.dto.UpdateDTO;
import com.company.kunuz.Comment.entity.CommentEntity;
import com.company.kunuz.Comment.repositoy.CommentRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import com.company.kunuz.Profile.dto.JwtDTO;
import com.company.kunuz.Profile.entity.ProfileEntity;
import com.company.kunuz.Profile.service.ProfileService;
import com.company.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;

    public CommentDTO create(CommentDTO comment,String token) {
        JwtDTO decode = JwtUtil.decode(token);
        ProfileEntity byIdProfile = profileService.getByUsername(decode.getUsername());
        ArticleEntity articleEntity = articleService.getByArticle(comment.getArticleId());
        comment.setProfileId(byIdProfile.getId());
        if (byIdProfile == null) throw new AppBadException("Profile not found");
        if (articleEntity == null) throw new AppBadException("Article not found");
        CommentEntity save = commentRepository.save(CommentEntity.dtoToEntity(comment));
        comment.setId(save.getId());
        comment.setCreatedDate(save.getCreatedDate());
        comment.setUpdateDate(save.getUpdateDate());
        return comment;
    }

    public CommentDTO update(Integer id, UpdateDTO dto,String token) {
        Optional<CommentEntity> byId = commentRepository.findById(id);
        JwtDTO decode = JwtUtil.decode(token);
        ProfileEntity byIdProfile = profileService.getByUsername(decode.getUsername());
        ArticleEntity articleEntity = articleService.getByArticle(dto.getArticle_id());
        if (articleEntity == null) throw new AppBadException("Article not found");
        if (byId.isPresent()) {
            CommentEntity commentEntity = byId.get();
            if (commentEntity.getProfileId().equals(byIdProfile.getId())){
                commentEntity.setContent(dto.getContent());
                commentEntity.setUpdateDate(LocalDateTime.now());
                commentRepository.save(commentEntity);
                CommentDTO commentDTO = CommentDTO.entityToDto(commentEntity);
                commentDTO.setId(commentEntity.getId());
                return commentDTO;
            }
        }
        return null;
    }

    public List<CommentDTO> getArticleId(String id) {
        List<CommentEntity> allByArticleId = commentRepository.findAllByArticleId(id);
        if (allByArticleId.isEmpty()) throw new AppBadException("Article not found");
        List<CommentDTO> list = new ArrayList<>();
        for (CommentEntity commentEntity : allByArticleId) {
            CommentDTO commentDTO = CommentDTO.entityToDto(commentEntity);
            list.add(commentDTO);
        }
        return list;
    }

    public Page<CommentAllDTO> getAllComment(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CommentEntity> entityList = commentRepository.getAll(pageRequest);
        Long total = entityList.getTotalElements();
        List<CommentAllDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : entityList) {
            CommentAllDTO commentAllDTO = new CommentAllDTO();
            commentAllDTO.setId(entity.getId());
            commentAllDTO.setContent(entity.getContent());
            commentAllDTO.setCreatedDate(entity.getCreatedDate());
            commentAllDTO.setUpdateDate(entity.getUpdateDate());
            commentAllDTO.setProfile(entity.getProfile());
            commentAllDTO.setArticle(entity.getArticle());
            commentAllDTO.setReplyId(entity.getReplyId());
            dtoList.add(commentAllDTO);
        }
        PageImpl page1 = new PageImpl<>(dtoList, pageRequest, total);
        return page1;
    }
}
