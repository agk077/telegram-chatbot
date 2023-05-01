package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.exception.UserNotFoundException;
import ru.liga.tinderserver.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User create(User user) {
       return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
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
        return (user1.getGenderForSearch() == null || user1.getGenderForSearch().equals(user2.getGender()));
    }
}
