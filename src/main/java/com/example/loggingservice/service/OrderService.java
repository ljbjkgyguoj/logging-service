package com.example.loggingservice.service;

import com.example.loggingservice.dto.OrderDto;

/**
 * Интерфейс для работы с заказами.
 */
public interface OrderService {

    /**
     * Создание нового заказа.
     *
     * @param orderDto данные о новом заказе
     */
    void create(OrderDto orderDto);

    /**
     * Получение заказа по идентификатору.
     *
     * @param id идентификатор заказа
     * @return данные о заказе
     */
    OrderDto findById(Long id);

    /**
     * Обновление уже существующего заказа.
     *
     * @param orderDto измененные данные о заказе
     */
    void update(OrderDto orderDto);

    /**
     * Удаление заказа.
     *
     * @param id идентификатор заказа
     */
    void delete(Long id);
}
