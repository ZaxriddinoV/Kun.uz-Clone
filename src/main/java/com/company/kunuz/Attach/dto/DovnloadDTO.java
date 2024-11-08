package com.company.kunuz.Attach.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
@Getter
@Setter
public class DovnloadDTO {
    private String Name;
    private Resource resource;
}
