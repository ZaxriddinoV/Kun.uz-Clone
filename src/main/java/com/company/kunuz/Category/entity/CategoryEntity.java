package com.company.kunuz.Category.entity;


import com.company.kunuz.Category.dto.CategoryDTO;
import com.company.kunuz.Region.entity.RegionEntity;
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
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer order_number;

    @Column(unique = true)
    private String name_uz;

    @Column(unique = true)
    private String name_ru;

    @Column(unique = true)
    private String name_en;

    private Boolean visible = Boolean.TRUE;

    private LocalDateTime created_date = LocalDateTime.now();

    public CategoryDTO convertToDTO(RegionEntity regionEntity) {
        return CategoryDTO.builder()
                .order_number(this.getOrder_number())
                .name_uz(this.getName_uz())
                .name_ru(this.getName_ru())
                .name_en(this.getName_en()).build();
    }
}
