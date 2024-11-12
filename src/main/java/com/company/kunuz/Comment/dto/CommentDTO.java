package com.company.kunuz.Comment.dto;

import com.company.kunuz.Comment.entity.CommentEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer id;
    @NotEmpty(message = "Content not empty")
    private String content;
    @NotNull(message = "profile id not null")
    private Integer profileId;
    @NotNull(message = "article id not null")
    private String articleId;
    private String replyId;
    private LocalDateTime updateDate;
    private LocalDateTime createdDate;
    private Boolean visible;

    public static CommentDTO entityToDto(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setProfileId(commentEntity.getProfileId());
        commentDTO.setArticleId(commentEntity.getArticleId());
        commentDTO.setReplyId(commentEntity.getReplyId());
        commentDTO.setCreatedDate(commentEntity.getCreatedDate());
        commentDTO.setUpdateDate(commentEntity.getUpdateDate());
        commentDTO.setVisible(commentEntity.getVisible());
        return commentDTO;
    }
}
