package com.service;

import java.util.List;

public interface IService<T> {
    List<T> findAll();

    T findById(int id);

    void save(T t);

    void remove(int id);
}
