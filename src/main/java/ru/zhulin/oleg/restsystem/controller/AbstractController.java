package ru.zhulin.oleg.restsystem.controller;

import org.springframework.http.ResponseEntity;
import ru.zhulin.oleg.restsystem.model.ParentEntity;

public class AbstractController<T extends ParentEntity> implements IController<T>{
    @Override
    public <N extends Number> ResponseEntity<T> get(N id) {
        return null;
    }

    @Override
    public ResponseEntity<Iterable<T>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<T> create(T item) {
        return null;
    }

    @Override
    public <N extends Number> ResponseEntity<T> update(T item, N id) {
        return null;
    }

    @Override
    public <N extends Number> ResponseEntity<T> delete(N id) {
        return null;
    }
}
