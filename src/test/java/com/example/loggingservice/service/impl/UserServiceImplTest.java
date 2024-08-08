package com.example.loggingservice.service.impl;

import com.example.loggingservice.aspect.LoggingAspect;
import com.example.loggingservice.converter.UserConverter;
import com.example.loggingservice.dto.UserDto;
import com.example.loggingservice.exception.BadRequestException;
import com.example.loggingservice.exception.NotFoundException;
import com.example.loggingservice.model.User;
import com.example.loggingservice.repository.OrderRepository;
import com.example.loggingservice.repository.UserRepository;
import com.example.loggingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private OrderRepository orderRepository;
    @Spy
    private LoggingAspect loggingAspect;
    UserService userService;

    @BeforeEach
    public void setUp() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, orderRepository, userConverter);
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(userServiceImpl);
        proxyFactory.addAspect(loggingAspect);
        DefaultAopProxyFactory proxyFactoryFactory = new DefaultAopProxyFactory();
        AopProxy aopProxy = proxyFactoryFactory.createAopProxy(proxyFactory);
        userService = (UserService) aopProxy.getProxy();
    }

    @Test
    void create_success() throws Throwable {
        //given
        UserDto dto = new UserDto();
        User entity = new User();

        //when
        when(userConverter.toEntity(dto)).thenReturn(entity);

        //then
        userService.create(dto);

        verify(userRepository, times(1)).save(entity);

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void findById_userIsNotExists_thrownNotFoundException() throws Throwable {
        //when
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> userService.findById(any()));

        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }

    @Test
    void findById_userIsExists_success() throws Throwable {
        //given
        UserDto expectedDto = new UserDto();
        User entity = new User();

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        when(userConverter.toDto(entity)).thenReturn(expectedDto);

        //then
        UserDto actualDto = userService.findById(any());

        assertEquals(expectedDto, actualDto);

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void update_userIsExists_success() throws Throwable {
        //given
        UserDto dto = new UserDto();
        User entity = new User();

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));

        //then
        userService.update(dto);
        verify(userRepository, times(1)).save(entity);

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void update_userIsNotExists_thrownNotFoundException() throws Throwable {
        //given
        UserDto userDto = new UserDto();

        //when
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> userService.update(userDto));

        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }

    @Test
    void delete_userIsExistsAndHasNoOrders_success() throws Throwable {
        //given
        User entity = new User();

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        when(orderRepository.exists(any())).thenReturn(false);

        //then
        userService.delete(any());

        verify(userRepository, times(1)).deleteById(any());

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void delete_userIsNotExists_thrownNotFoundException() throws Throwable {
        //when
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> userService.delete(any()));

        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }

    @Test
    void delete_userHasOrders_thrownBadRequestException() throws Throwable {
        //given
        User entity = new User();

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(entity));
        when(orderRepository.exists(any())).thenReturn(true);

        //then
        assertThrows(BadRequestException.class, () -> userService.delete(any()));

        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }
}