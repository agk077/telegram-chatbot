package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.tinderserver.entity.User;

import java.util.List;
import java.util.stream.Collectors;

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
        return userService.findAll()
                .stream()
                .filter(checkUser -> !checkUser.getId().equals(userId) &&
                        relationService.findRelationByUsers(user.getId(), checkUser.getId()).isEmpty() &&
                        userService.isUsersMatch(user, checkUser)
                ).collect(Collectors.toList());

    }

}
