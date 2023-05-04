package ru.liga.tinderserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.liga.tinderserver.dto.UserDto;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return modelMapper.map(userService.findById(id), UserDto.class);
    }

    @PostMapping("/")
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return modelMapper.map(userService.create(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return modelMapper.map(userService.update(id, modelMapper.map(userDto, User.class)), UserDto.class);
    }

}
