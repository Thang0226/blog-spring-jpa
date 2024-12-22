package com.repository;

import java.util.List;

public interface IRepository <T> {
    List<T> findAll();

    T findById(int id);

    void save(T t);

    void remove(int id);
}
