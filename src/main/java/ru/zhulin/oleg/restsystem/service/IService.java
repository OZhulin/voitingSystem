package ru.zhulin.oleg.restsystem.service;

import ru.zhulin.oleg.restsystem.model.ParentEntity;

import java.util.List;

public interface IService<T extends ParentEntity, N extends Number> {
    T get(N id);
    List<T> getAll();
    T create(T item);
    T update(T item, N id);
    void delete(N id);
    void deleteAll();
}
