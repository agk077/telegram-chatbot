package ru.liga.tinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.tinderserver.dto.UserDto;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
