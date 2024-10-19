package com.company.kunuz.ArticleType.dto;

import com.company.kunuz.ArticleType.entity.ArticleTypeEntity;
import com.company.kunuz.Category.entity.CategoryEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ArticleTypeDTO {

   private Integer id;
   @NotNull(message = "Order must not be empty")
   private Integer order_number;
   @NotEmpty(message = "name uz  must not be empty")
   private String name_uz;
   @NotEmpty(message = "name ru  must not be empty")
   private String name_ru;
   @NotEmpty(message = "name en  must not be empty")
   private String name_en;

   public ArticleTypeEntity convertToEntity() {
      return ArticleTypeEntity.builder()
              .order_number(this.order_number)
              .name_uz(this.name_uz)
              .name_en(this.name_en)
              .name_ru(this.name_ru)
              .created_date(LocalDateTime.now())
              .visible(Boolean.TRUE).build();
   }

}
