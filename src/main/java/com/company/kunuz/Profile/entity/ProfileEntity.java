package com.company.kunuz.Profile.entity;


import com.company.kunuz.Attach.entity.AttachEntity;
import com.company.kunuz.Profile.dto.ProfileDTO;
import com.company.kunuz.Profile.enums.ProfileRole;
import com.company.kunuz.Profile.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status = ProfileStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime created_date;
    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;


    public ProfileDTO convertToDTO() {
        return ProfileDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .surname(this.getSurname())
                .username(this.getUsername())
                .password(this.getPassword())
                .status(this.getStatus())
                .role(this.getRole()).build();
    }
}
