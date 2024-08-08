package com.example.loggingservice.service.impl;

import com.example.loggingservice.aspect.LoggingAspect;
import com.example.loggingservice.converter.OrderConverter;
import com.example.loggingservice.dto.OrderDto;
import com.example.loggingservice.exception.NotFoundException;
import com.example.loggingservice.model.Order;
import com.example.loggingservice.repository.OrderRepository;
import com.example.loggingservice.service.OrderService;
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
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderConverter orderConverter;
    @Spy
    private LoggingAspect loggingAspect;
    OrderService orderService;

    @BeforeEach
    public void setUp() {
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, orderConverter);
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(orderServiceImpl);
        proxyFactory.addAspect(loggingAspect);
        DefaultAopProxyFactory proxyFactoryFactory = new DefaultAopProxyFactory();
        AopProxy aopProxy = proxyFactoryFactory.createAopProxy(proxyFactory);
        orderService = (OrderService) aopProxy.getProxy();
    }


    @Test
    void create_success() {
        //given
        OrderDto dto = new OrderDto();
        Order entity = new Order();

        //when
        when(orderConverter.toEntity(dto)).thenReturn(entity);

        //then
        orderService.create(dto);
        verify(orderRepository, times(1)).save(entity);
    }

    @Test
    void findById_orderIsNotExists_thrownNotFoundException() {
        //when
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> orderService.findById(any()));
    }

    @Test
    void findById_orderIsExists_success() throws Throwable {
        //given
        OrderDto expectedDto = new OrderDto();
        Order entity = new Order();

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(entity));
        when(orderConverter.toDto(entity)).thenReturn(expectedDto);

        //then
        OrderDto actualDto = orderService.findById(any());
        assertEquals(expectedDto, actualDto);

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void update_orderIsExists_success() throws Throwable {
        //given
        OrderDto dto = new OrderDto();
        Order entity = new Order();

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(entity));

        //then
        orderService.update(dto);
        verify(orderRepository, times(1)).save(entity);

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void update_orderIsNotExists_thrownNotFoundException() throws Throwable {
        //given
        OrderDto orderDto = new OrderDto();

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> orderService.update(orderDto));

        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }

    @Test
    void delete_orderIsExists_success() throws Throwable {
        //given
        Order entity = new Order();

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(entity));

        //then
        orderService.delete(any());
        verify(orderRepository, times(1)).deleteById(any());

        verify(loggingAspect, times(1)).around(any());
    }

    @Test
    void delete_orderIsNotExists_thrownNotFoundException() throws Throwable {
        //when
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> orderService.delete(any()));


        verify(loggingAspect, times(1)).around(any());
        verify(loggingAspect, times(1)).addLogAfterThrowing(any());
    }

}