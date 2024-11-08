package com.company.kunuz.Comment.entity;

import com.company.kunuz.Article.entity.ArticleEntity;
import com.company.kunuz.Comment.dto.CommentDTO;
import com.company.kunuz.Profile.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    /*
     * Join Profile id
     * */
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    /*
    * Join Article id
    * */
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private String replyId;
    @Column(name = "visible")
    private Boolean visible;

    public static CommentEntity dtoToEntity(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setProfileId(commentDTO.getProfileId());
        commentEntity.setArticleId(commentDTO.getArticleId());
        commentEntity.setReplyId(commentDTO.getReplyId());

        commentEntity.setCreatedDate(LocalDateTime.now());
        commentEntity.setUpdateDate(LocalDateTime.now());
        commentEntity.setVisible(Boolean.TRUE);
        return commentEntity;
    }
}
