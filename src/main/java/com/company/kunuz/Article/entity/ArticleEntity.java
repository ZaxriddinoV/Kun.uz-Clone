package com.company.kunuz.Article.entity;

import com.company.kunuz.Article.enums.ArticleStatus;
import com.company.kunuz.Attach.entity.AttachEntity;
import com.company.kunuz.Category.entity.CategoryEntity;
import com.company.kunuz.Profile.entity.ProfileEntity;
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
    @Column(name = "content",columnDefinition = "TEXT")
    private String content;
    @Column(name = "shared_count")
    private Integer shared_count;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", updatable = false, insertable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",updatable = false, insertable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private CategoryEntity category;

    @OneToMany
    private List<ArticleTypeEntity> article_type_id;
    @Column(name = "moderator_id")

    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", updatable = false, insertable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
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
