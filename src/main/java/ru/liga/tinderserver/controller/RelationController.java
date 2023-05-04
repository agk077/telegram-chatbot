package ru.liga.tinderserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.liga.tinderserver.dto.RelationDto;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.service.RelationService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/relations")
public class RelationController {

    private final RelationService relationService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<RelationDto> findAll() {
        return relationService.findAll().stream()
                .map(relation -> modelMapper.map(relation, RelationDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RelationDto findById(@PathVariable Long id) {
        return modelMapper.map(relationService.findById(id), RelationDto.class);
    }

    @GetMapping("user/{userId}")
    public List<RelationDto> findAllUserId(@PathVariable Long userId) {
        return relationService.findAllByUserId(userId).stream()
                .map(relation -> modelMapper.map(relation, RelationDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("user/like/{userId}")
    public List<RelationDto> findLikeUserId(@PathVariable Long userId) {
        return relationService.findLikeByUserId(userId).stream()
                .map(relation -> modelMapper.map(relation, RelationDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("selected/{selectedUserId}")
    public List<RelationDto> findAllSelectedUserID(@PathVariable Long selectedUserId) {
        return relationService.findAllBySelectedUserId(selectedUserId).stream()
                .map(relation -> modelMapper.map(relation, RelationDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("mutual/{userId}")
    public List<RelationDto> findMutualFeeling(@PathVariable Long userId) {
        return relationService.findMutualFeeling(userId).stream()
                .map(relation -> modelMapper.map(relation, RelationDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public RelationDto create(@Valid @RequestBody RelationDto relationDto) {
        return modelMapper.map(relationService.create(modelMapper.map(relationDto, Relation.class)), RelationDto.class);
    }

    @PutMapping("/{id}")
    public RelationDto update(@PathVariable Long id, @Valid @RequestBody RelationDto relationDto) {
        return modelMapper.map(relationService.update(id, modelMapper.map(relationDto, Relation.class)), RelationDto.class);
    }

}
