package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.User;

import java.util.Collection;
//1675674664

public interface UserService {
    User create(User user);
    Collection<User> list();
    User get(Integer uid);
    User updateByUid(User user, Integer uid);
    Boolean delete(Integer uid);
}
