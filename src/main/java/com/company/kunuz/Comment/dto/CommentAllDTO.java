package com.company.kunuz.Comment.dto;

import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Profile.entity.ProfileEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentAllDTO {
    private Integer id;
    private String content;
    private ProfileEntity profile;
    private ArticleEntity article;
    private String replyId;
    private LocalDateTime updateDate;
    private LocalDateTime createdDate;
}
