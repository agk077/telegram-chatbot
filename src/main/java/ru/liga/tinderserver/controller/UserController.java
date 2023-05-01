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
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return convertToUserDto(userService.findById(id));
    }

    @PostMapping("/")
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return convertToUserDto(userService.create(convertToUser(userDto)));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return convertToUserDto(userService.update(id, convertToUser(userDto)));
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
