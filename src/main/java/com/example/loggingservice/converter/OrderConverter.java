package com.example.loggingservice.converter;

import com.example.loggingservice.dto.OrderDto;
import com.example.loggingservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Конвертация {@link Order} и {@link OrderDto}.
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    /**
     * Преобразовать сущность в дто.
     *
     * @param order сущность {@link Order}
     * @return дто {@link OrderDto}
     */
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    /**
     * Преобразовать дто в сущность.
     *
     * @param orderDto дто {@link OrderDto}
     * @return сущность {@link Order}
     */
    @Mapping(target = "user.id", source = "orderDto.userId")
    Order toEntity(OrderDto orderDto);

    /**
     * Дополнить сущность новыми значениями для обновления записи.
     *
     * @param order    сущность {@link Order}
     * @param orderDto дто {@link OrderDto}
     */
    @Mapping(target = "order.description", source = "orderDto.description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order.status", source = "orderDto.status", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order.id", ignore = true)
    void toUpdateEntity(@MappingTarget Order order, OrderDto orderDto);
}
