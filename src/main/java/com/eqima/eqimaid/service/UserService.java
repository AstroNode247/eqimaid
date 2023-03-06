package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.User;
import org.bson.types.ObjectId;

import java.util.Collection;
//1675674664

public interface UserService {
    User create(User user);
    Collection<User> list();
    User get(String uid);
    User updateByUid(User user, String uid);
    Boolean delete(String uid);
}
