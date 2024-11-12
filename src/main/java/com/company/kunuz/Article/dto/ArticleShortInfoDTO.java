package com.company.kunuz.Article.dto;

import com.company.kunuz.Attach.dto.AttachDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private String image;
    private LocalDateTime publishedDate;

}
