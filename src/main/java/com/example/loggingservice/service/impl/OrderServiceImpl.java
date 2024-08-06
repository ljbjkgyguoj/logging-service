package com.example.loggingservice.service.impl;

import com.example.loggingservice.converter.OrderConverter;
import com.example.loggingservice.dto.OrderDto;
import com.example.loggingservice.exception.NotFoundException;
import com.example.loggingservice.model.Order;
import com.example.loggingservice.repository.OrderRepository;
import com.example.loggingservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link OrderService}
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    public static final String EXCEPTION_MESSAGE = "Заказ с идентификатором %s не был найден";
    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    @Override
    public void create(OrderDto orderDto) {
        Order order = orderConverter.toEntity(orderDto);
        orderRepository.save(order);
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(EXCEPTION_MESSAGE, id)));
        return orderConverter.toDto(order);
    }

    @Override
    public void update(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(EXCEPTION_MESSAGE, orderDto.getId())));
        orderConverter.toUpdateEntity(order, orderDto);
        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(EXCEPTION_MESSAGE, id)));
        orderRepository.deleteById(id);
    }
}
