package com.eqima.eqimaid.service.implementation;

import com.eqima.eqimaid.exception.UserExistException;
import com.eqima.eqimaid.exception.UserNotFoundException;
import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.repository.UserRepository;
import com.eqima.eqimaid.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        log.info("Save new user : {}", user.getUid());
        User count = userRepository.findByUid(user.getUid());
        if (count != null) {
            throw new UserExistException("L'identifiant de l'utilisateur existe déjà");
        }
        return userRepository.save(user);
    }

    @Override
    public Collection<User> list() {
        log.info("Fetch all users");
        return userRepository.findAll();
    }

    @Override
    public User get(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            throw new UserNotFoundException("Cette utilisateur n'existe pas");
        }
        return user;
    }

    @Override
    public User updateByUid(User user, String uid) {
        log.info("Update user : {}", uid);
        User newUser = userRepository.findByUid(uid);
        newUser.setUid(uid);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Boolean delete(String uid) {
        log.info("Delete user : {}", uid);
        userRepository.deleteByUid(uid);
        return Boolean.TRUE;
    }
}
