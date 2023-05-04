package ru.liga.tinderserver.exception;

public class RelationNotFoundException extends RuntimeException {
    public RelationNotFoundException(Long id) {
        super("Отношения не найдены id = " + id);
    }
}
