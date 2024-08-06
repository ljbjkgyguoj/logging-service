package com.example.loggingservice.service;

import com.example.loggingservice.dto.UserDto;

/**
 * Интерфейс для работы с пользователями.
 */
public interface UserService {

    /**
     * Создание нового пользователя.
     *
     * @param userDto данные о новом пользователе
     */
    void create(UserDto userDto);

    /**
     * Получение пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return данные о пользователе
     */
    UserDto findById(Long id);

    /**
     * Обновление данных уже существующего пользователя.
     *
     * @param userDto измененные данные о пользователе
     */
    void update(UserDto userDto);

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя
     */
    void delete(Long id);
}
