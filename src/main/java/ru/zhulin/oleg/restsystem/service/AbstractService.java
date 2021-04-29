package ru.zhulin.oleg.restsystem.service;

import ru.zhulin.oleg.restsystem.model.ParentEntity;

import java.util.List;

public class AbstractService<T extends ParentEntity> implements IService<T>{
    @Override
    public <N extends Number> T get(N id) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T create(T item) {
        return null;
    }

    @Override
    public <N extends Number> T update(T item, N id) {
        return null;
    }

    @Override
    public <N extends Number> void delete(N id) {

    }

    @Override
    public void deleteAll() {

    }
}
