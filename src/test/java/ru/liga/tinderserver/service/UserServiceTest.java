package ru.liga.tinderserver.service;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.tinderserver.entity.Gender;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User[] users =new User[]{
            User.builder()
                    .id(1L)
                    .name("Алла")
                    .gender(Gender.FEMALE)
                    .genderForSearch(Gender.MALE)
                    .build(),
            User.builder()
                    .id(2L)
                    .name("Роза")
                    .gender(Gender.FEMALE)
                    .genderForSearch(Gender.MALE)
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
                    .build()

    };
   


    @Test
    public void isUsersMatchTest(){
        Mockito.doReturn(Optional.ofNullable(users[0])).when(userRepository).findById(1L);
        Mockito.doReturn(Optional.ofNullable(users[1])).when(userRepository).findById(2L);
        Mockito.doReturn(Optional.ofNullable(users[2])).when(userRepository).findById(3L);
        Mockito.doReturn(Optional.ofNullable(users[3])).when(userRepository).findById(4L);


        assertTrue(userService.isUsersMatch(userRepository.findById(1L).get(),userRepository.findById(3L).get()));
        assertFalse(userService.isUsersMatch(userRepository.findById(1L).get(),userRepository.findById(2L).get()));
        assertTrue(userService.isUsersMatch(userRepository.findById(1L).get(),userRepository.findById(4L).get()));
        assertFalse(userService.isUsersMatch(userRepository.findById(3L).get(),userRepository.findById(4L).get()));
        assertFalse(userService.isUsersMatch(userRepository.findById(4L).get(),userRepository.findById(3L).get()));

    }


}