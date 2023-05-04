package ru.liga.tinderserver.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Пользователь не найден id = " + id);
    }
}
