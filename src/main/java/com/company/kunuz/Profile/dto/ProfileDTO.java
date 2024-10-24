package com.company.kunuz.Profile.dto;

import com.company.kunuz.Profile.entity.ProfileEntity;
import com.company.kunuz.Profile.enums.ProfileRole;
import com.company.kunuz.Profile.enums.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Integer id;
    @NotBlank(message = "Name not found")
    private String name;
    @NotBlank(message = "Surname not found")
    private String surname;
    @NotBlank(message = "Email not found")
    private String email;
    @NotBlank(message = "Password not found")
    private String password;
    private ProfileStatus status;
    private ProfileRole role;


    public ProfileEntity mapToEntity() {
        return ProfileEntity.builder()
                .name(this.getName())
                .surname(this.getSurname())
                .password(this.getPassword())
                .role(this.getRole())
                .email(this.getEmail())
                .status(this.getStatus())
                .created_date(LocalDateTime.now())
                .visible(Boolean.TRUE)
                .build();
    }
}