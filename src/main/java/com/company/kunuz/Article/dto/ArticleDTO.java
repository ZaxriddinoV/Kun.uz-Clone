package com.company.kunuz.Article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ArticleDTO {
    private String id;
    @NotBlank(message = "title not found")
    private String title;
    @NotBlank(message = "content not found")
    private String description;
    @NotBlank(message = "content not found")
    private String content;
    @NotNull
    private String image_id;
    @NotNull
    private Integer region_id;
    @NotNull
    private Integer category_id;
    private List<Integer> articleType;
}
