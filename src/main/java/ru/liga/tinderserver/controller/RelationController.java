package ru.liga.tinderserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.tinderserver.dto.RelationDto;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.service.RelationService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/relation")
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    public List<Relation> findAll() {
        return relationService.findAll();
    }

    @GetMapping("/{id}")
    public Relation findById(@PathVariable Long id) {
        return relationService.findById(id);
    }

    @GetMapping("user/{userId}")
    public List<Relation> findAllUserId(@PathVariable Long userId) {
        return relationService.findAllByUserId(userId);
    }

    @GetMapping("user/like/{userId}")
    public List<Relation> findLikeUserId(@PathVariable Long userId) {
        return relationService.findLikeByUserId(userId);
    }

    @GetMapping("selected/{selectedUserId}")
    public List<Relation> findAllSelectedUserID(@PathVariable Long selectedUserId) {
        return relationService.findAllBySelectedUserId(selectedUserId);
    }

    @GetMapping("mutual/{userId}")
    public List<Relation> findMutualFeeling(@PathVariable Long userId) {
        return relationService.findMutualFeeling(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody RelationDto relationDto) {
        relationService.create(relationDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,@Valid @RequestBody RelationDto relationDto) {
        relationService.update(id, relationDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
