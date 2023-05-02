package ru.liga.tinderserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.tinderserver.entity.Gender;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.repository.RelationRepository;
import ru.liga.tinderserver.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FormServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RelationRepository relationRepository;

    @InjectMocks
    UserService userService;

    @InjectMocks
    RelationService relationService;

    FormService formService;

    User[] users;
    Relation[] relations;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
        relationService = new RelationService(relationRepository);
        formService = new FormService(userService, relationService);
        users = getUsers();
        relations = getRelations();
    }

    @Test
    void findNewFormTest() {
        Mockito.doReturn(Optional.ofNullable(users[0])).when(userRepository).findById(1L);
        Mockito.doReturn(Arrays.asList(users)).when(userRepository).findAll();
        Mockito.doReturn(Arrays.stream(relations).filter(relation -> relation.getUserId().equals(1L)).collect(Collectors.toList())).when(relationRepository).findAllByUserId(1L);

        assertEquals(2, formService.findNewForm(1L).size());
        assertNotNull(formService.findNewForm(1L).stream().filter(user -> user.getId().equals(4L)).findFirst());
        assertNotNull(formService.findNewForm(1L).stream().filter(user -> user.getId().equals(5L)).findFirst());

    }

    private User[] getUsers() {
        return new User[]{
                User.builder()
                        .id(1L)
                        .name("Алла")
                        .gender(Gender.FEMALE)
                        .genderForSearch(Gender.MALE)
                        .build(),
                User.builder()
                        .id(2L)
                        .name("Петр")
                        .gender(Gender.MALE)
                        .genderForSearch(Gender.FEMALE)
                        .build(),
                User.builder()
                        .id(3L)
                        .name("Иван")
                        .gender(Gender.MALE)
                        .genderForSearch(Gender.FEMALE)
                        .build(),
                User.builder()
                        .id(4L)
                        .name("Артур")
                        .gender(Gender.MALE)
                        .build(),
                User.builder()
                        .id(5L)
                        .name("Кирилл")
                        .gender(Gender.MALE)
                        .genderForSearch(Gender.FEMALE)
                        .build(),
                User.builder()
                        .id(6L)
                        .name("Яна")
                        .gender(Gender.FEMALE)
                        .build(),
                User.builder()
                        .id(7L)
                        .name("Мария")
                        .gender(Gender.FEMALE)
                        .genderForSearch(Gender.MALE)
                        .build()

        };
    }

    private Relation[] getRelations() {
        return new Relation[]{
                Relation.builder()
                        .id(1L)
                        .userId(1L)
                        .selectedUserId(2L)
                        .sympathy(false)
                        .build(),
                Relation.builder()
                        .id(1L)
                        .userId(1L)
                        .selectedUserId(3L)
                        .sympathy(true)
                        .build()
        };
    }
}