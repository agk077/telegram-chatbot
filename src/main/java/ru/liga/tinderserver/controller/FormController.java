package ru.liga.tinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.service.FormService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/forms")
public class FormController {

    private final FormService formService;

    //TODO: юзер айди
    @GetMapping("new/{id}")
    public List<User> findNewForm(@PathVariable Long id){
        return formService.findNewForm(id);
    }

}
