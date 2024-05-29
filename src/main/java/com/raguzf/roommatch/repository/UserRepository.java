package com.raguzf.roommatch.repository;

import java.util.Collection;

import com.raguzf.roommatch.domain.User;

public interface UserRepository <T extends User> {
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Integer id);
    T update(T data);
    Boolean delete(Integer id);

    
}
