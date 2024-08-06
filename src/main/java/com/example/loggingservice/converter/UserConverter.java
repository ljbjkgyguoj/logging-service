package com.example.loggingservice.converter;

import com.example.loggingservice.dto.UserDto;
import com.example.loggingservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Конвертация {@link User} и {@link UserDto}.
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * Преобразовать сущность в дто.
     *
     * @param user сущность {@link User}
     * @return дто {@link UserDto}
     */
    UserDto toDto(User user);

    /**
     * Преобразовать дто в сущность.
     *
     * @param userDto дто {@link UserDto}
     * @return сущность {@link User}
     */
    User toEntity(UserDto userDto);

    /**
     * Дополнить сущность новыми значениями для обновления записи.
     *
     * @param user    сущность {@link User}
     * @param userDto дто {@link UserDto}
     */
    @Mapping(target = "user.name", source = "userDto.name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user.email", source = "userDto.email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user.id", ignore = true)
    void toUpdateEntity(@MappingTarget User user, UserDto userDto);
}
