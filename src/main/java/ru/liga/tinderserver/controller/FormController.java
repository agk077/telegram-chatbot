package ru.liga.tinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.tinderserver.dto.UserDto;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.service.FormService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/forms")
public class FormController {

    private final FormService formService;
    private final ModelMapper modelMapper;

    @GetMapping("unseen/{userId}")
    public List<UserDto> findNewForm(@PathVariable Long userId) {
        return formService.findNewForm(userId).stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

}
