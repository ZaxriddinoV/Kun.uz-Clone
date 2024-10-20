package com.company.kunuz.Article.entity;

import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Region.entity.RegionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
    @Column(name = "shared_count")
    private Integer shared_count;
    @Column(name = "image_id")
    private Integer image_id;
    @ManyToOne
    private RegionEntity region_id;
    @ManyToOne
    private CategoryEntity category_id;
    @OneToMany
    private List<ArticleTypeEntity> article_type_id;
    @Column(name = "moderator_id")
    private Integer moderator_id;
    @Column(name = "publisher_id")
    private Integer publisher_id;
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NotPublished;
    @Column(name = "created_date")
    private LocalDateTime created_date;
    @Column(name = "published_date")
    private LocalDateTime published_date;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "view_count")
    private Integer view_count;


}
