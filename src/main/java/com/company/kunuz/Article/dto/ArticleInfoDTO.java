package com.company.kunuz.Article.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ArticleInfoDTO {

    private Integer type;
    @NotNull
    private List<String> Ids;
    @NotNull
    private Integer number;
}
