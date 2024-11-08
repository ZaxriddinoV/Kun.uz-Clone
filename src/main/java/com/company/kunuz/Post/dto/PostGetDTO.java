package com.company.kunuz.Post.dto;

import com.company.kunuz.Attach.entity.AttachEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostGetDTO {
    private Integer id;
    private String title;
    private String content;
    private List<AttachEntity> imageIdList;
}
