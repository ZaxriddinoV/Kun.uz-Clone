package com.company.kunuz.UsernameHistory.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsAuthResponseDTO {
    private SmsAuthTokenDTO data;
}

