package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.tinderserver.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {

    private final UserService userService;
    private final RelationService relationService;

    /**
     * Метод получения непросмотренных пользователем анкет
     *
     * @param userId
     * @return список пользователей
     */
    public List<User> findNewForm(Long userId) {
        User user = userService.findById(userId);
        log.info("Поиск непросмотренных анкет для пользователя с id = " + userId);
        return userService.findAll()
                .stream()
                .filter(checkUser -> !checkUser.getId().equals(userId) &&
                        relationService.findRelationByUsers(user.getId(), checkUser.getId()) == null &&
                        userService.isUsersMatch(user, checkUser)
                ).collect(Collectors.toList());

    }

}
