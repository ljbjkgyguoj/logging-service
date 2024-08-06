package com.example.loggingservice.service.impl;

import com.example.loggingservice.converter.UserConverter;
import com.example.loggingservice.dto.UserDto;
import com.example.loggingservice.exception.BadRequestException;
import com.example.loggingservice.exception.NotFoundException;
import com.example.loggingservice.model.Order;
import com.example.loggingservice.model.User;
import com.example.loggingservice.repository.OrderRepository;
import com.example.loggingservice.repository.UserRepository;
import com.example.loggingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserService}
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "Пользователь с идентификатором %s не был найден";
    public static final String BAD_REQUEST_EXCEPTION_MESSAGE =
            "Пользователь с идентификатором %s не может быть удален, так как имеет активные заказы";
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private UserConverter userConverter;

    @Override
    public void create(UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, id)));
        return userConverter.toDto(user);
    }

    @Override
    public void update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, userDto.getId())));
        userConverter.toUpdateEntity(user, userDto);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, id)));

        if (doesUserHasAnOrder(id)) {
            throw new BadRequestException(String.format(BAD_REQUEST_EXCEPTION_MESSAGE, id));
        }

        userRepository.deleteById(id);
    }

    private boolean doesUserHasAnOrder(Long userId) {
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);

        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Order> example = Example.of(order, matcher);

        return orderRepository.exists(example);
    }
}
