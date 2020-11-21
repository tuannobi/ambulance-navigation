package com.system.ambulancenavigation.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    //  boolean existsById(ID id);
    long count();
//  void deleteById(ID id);
//  void delete(T t);
}
