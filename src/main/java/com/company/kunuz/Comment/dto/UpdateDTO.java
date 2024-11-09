package com.company.kunuz.Comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDTO {
    private String content;
    private UUID article_id;
}
