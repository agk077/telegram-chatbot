package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.repository.UserRepository;
import ru.liga.tinderserver.dto.UserDto;
import ru.liga.tinderserver.entity.User;
import ru.liga.tinderserver.exception.UserNotFoundException;

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
    public void create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setDesc(userDto.getDesc());
        user.setGender(userDto.getGender());
        user.setGenderForSearch(userDto.getGenderForSearch());
        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, UserDto userDto) {
        User user = findById(id);
        user.setName(userDto.getName());
        user.setDesc(userDto.getDesc());
        user.setGender(userDto.getGender());
        user.setGenderForSearch(userDto.getGenderForSearch());
        userRepository.save(user);
    }

    public boolean isUsersMatch(User user, User selectedUser) {
        return (user.getGenderForSearch() == null || user.getGenderForSearch().equals(selectedUser.getGender()));
    }
}
