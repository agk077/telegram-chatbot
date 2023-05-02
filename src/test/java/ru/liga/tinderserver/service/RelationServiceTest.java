package ru.liga.tinderserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.repository.RelationRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RelationServiceTest {

    @Mock
    private RelationRepository relationRepository;

    @InjectMocks
    RelationService relationService;

    Relation[] relations;

    @BeforeEach
    public void setup() {
        relations = getRelations();
    }

    @Test
    void isMutualLikeTest() {
        Mockito.doReturn(Optional.ofNullable(relations[0])).when(relationRepository).findById(1L);
        Mockito.doReturn(Optional.ofNullable(relations[1])).when(relationRepository).findById(2L);
        Mockito.doReturn(Optional.ofNullable(relations[2])).when(relationRepository).findById(3L);
        Mockito.doReturn(Optional.ofNullable(relations[3])).when(relationRepository).findById(4L);
        Mockito.doReturn(Optional.ofNullable(relations[4])).when(relationRepository).findById(5L);
        Mockito.doReturn(Arrays.asList(relations).stream()
                        .filter(relation -> relation.getUserId().equals(1L))
                        .collect(Collectors.toList()))
                .when(relationRepository).findAllByUserId(1L);
        Mockito.doReturn(Arrays.asList(relations).stream()
                        .filter(relation -> relation.getUserId().equals(3L))
                        .collect(Collectors.toList()))
                .when(relationRepository).findAllByUserId(3L);
        Mockito.doReturn(Arrays.asList(relations).stream()
                        .filter(relation -> relation.getUserId().equals(5L))
                        .collect(Collectors.toList()))
                .when(relationRepository).findAllByUserId(5L);


        assertFalse(relationService.isMutualLike(relationRepository.findById(1L).get().getUserId(), relationRepository.findById(2L).get().getUserId()));
        assertFalse(relationService.isMutualLike(relationRepository.findById(1L).get().getUserId(), relationRepository.findById(4L).get().getUserId()));
        assertFalse(relationService.isMutualLike(relationRepository.findById(1L).get().getUserId(), relationRepository.findById(5L).get().getUserId()));
        assertTrue(relationService.isMutualLike(relationRepository.findById(3L).get().getUserId(), relationRepository.findById(2L).get().getUserId()));
        assertTrue(relationService.isMutualLike(relationRepository.findById(5L).get().getUserId(), relationRepository.findById(1L).get().getUserId()));
        assertTrue(relationService.isMutualLike(relationRepository.findById(1L).get().getUserId(), relationRepository.findById(3L).get().getUserId()));

    }

    private Relation[] getRelations() {
        return new Relation[]{
                Relation.builder()
                        .id(1L)
                        .userId(1L)
                        .selectedUserId(3L)
                        .sympathy(true)
                        .build(),
                Relation.builder()
                        .id(2L)
                        .userId(1L)
                        .selectedUserId(4L)
                        .sympathy(false)
                        .build(),
                Relation.builder()
                        .id(3L)
                        .userId(3L)
                        .selectedUserId(1L)
                        .sympathy(true)
                        .build(),
                Relation.builder()
                        .id(4L)
                        .userId(4L)
                        .selectedUserId(1L)
                        .sympathy(true)
                        .build(),
                Relation.builder()
                        .id(5L)
                        .userId(5L)
                        .selectedUserId(1L)
                        .sympathy(true)
                        .build()

        };
    }
}