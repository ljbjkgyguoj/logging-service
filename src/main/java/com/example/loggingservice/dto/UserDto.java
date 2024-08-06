package com.example.loggingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные о пользователе")
public class UserDto {
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Электронная почта")
    private String email;
}
