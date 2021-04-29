package ru.zhulin.oleg.restsystem.controller;

import org.springframework.http.ResponseEntity;
import ru.zhulin.oleg.restsystem.model.ParentEntity;

public interface IController<T extends ParentEntity> {
    <N extends Number> ResponseEntity<T> get(N id);
    ResponseEntity<Iterable<T>> getAll();
    ResponseEntity<T> create(T item);
    <N extends Number> ResponseEntity<T> update(T item, N id);
    <N extends Number> ResponseEntity<T> delete(N id);
}
