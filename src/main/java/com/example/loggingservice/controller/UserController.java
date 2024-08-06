package com.example.loggingservice.controller;

import com.example.loggingservice.dto.UserDto;
import com.example.loggingservice.service.UserService;
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
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "Контроллер для работы с пользователями")
public class UserController {

    private UserService userService;

    @GetMapping("/get/{id}")
    @Operation(summary = "Получить пользователя по идентификатору")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Создать нового пользователя")
    public void createUser(@RequestBody UserDto userDto) {
        userService.create(userDto);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить данные уже существующего пользователя")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить пользователя")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
