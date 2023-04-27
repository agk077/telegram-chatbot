package ru.liga.tinderserver.exception;

public class RelationNotFoundException extends RuntimeException {
    public RelationNotFoundException(Long id) {
        super("Not found relation with id = " + id);
    }
}
