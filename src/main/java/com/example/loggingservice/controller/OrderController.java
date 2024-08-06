package com.example.loggingservice.controller;

import com.example.loggingservice.dto.OrderDto;
import com.example.loggingservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Tag(name = "Контроллер для работы с заказами")
public class OrderController {

    private OrderService orderService;

    @GetMapping("/get/{id}")
    @Operation(summary = "Получить заказ по идентификатору")
    public OrderDto getOrderById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Создать новый заказ")
    public void createOrder(@RequestBody OrderDto orderDto) {
        orderService.create(orderDto);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить уже существующий заказ")
    public void updateOrder(@RequestBody OrderDto orderDto) {
        orderService.update(orderDto);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить заказ")
    public void deleteOrderById(@PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
