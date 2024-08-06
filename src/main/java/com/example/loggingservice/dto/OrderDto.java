package com.example.loggingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные о заказе")
public class OrderDto {
    @Schema(description = "Идентификатор заказа")
    private Long id;
    @Schema(description = "Описание заказа")
    private String description;
    @Schema(description = "Статус заказа")
    private String status;
    @Schema(description = "Идентификатор пользователя")
    private Long userId;
}
