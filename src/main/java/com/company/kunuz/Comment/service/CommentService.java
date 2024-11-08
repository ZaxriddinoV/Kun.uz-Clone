package com.company.kunuz.Comment.service;

import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Article.service.ArticleService;
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
import org.springframework.stereotype.Service;

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

    public CommentDTO update(UpdateDTO dto) {
        
        return null;
    }
}
