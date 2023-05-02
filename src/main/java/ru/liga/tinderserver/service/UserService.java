package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.exception.UserNotFoundException;
import ru.liga.tinderserver.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        log.info("поиск всех пользователей");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        log.info("поиск пользователя с id = " + id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User create(User user) {
        log.info("создаем пользователя: " + user);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        try {
            user.setId(id);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }
        log.info("изменяем пользователя id = " + id + " " + user);
        return userRepository.save(user);
    }

    /**
     * Метод определяет подходит ли
     * первому пользователю второй
     * по предпочтениям (genderForSearch)
     *
     * @param user1
     * @param user2
     * @return список отношений
     */
    public boolean isUsersMatch(User user1, User user2) {
        log.info("выясняем подходят ли пользователи друг другу " + user1.getId() + " и " + user2.getId());
        return ((user1.getGenderForSearch() == null || user1.getGenderForSearch().equals(user2.getGender()))
                && (user2.getGenderForSearch() == null || user2.getGenderForSearch().equals(user1.getGender())));
    }
}
