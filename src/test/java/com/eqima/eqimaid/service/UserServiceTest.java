package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.repository.UserRepository;
import com.eqima.eqimaid.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void canCreateUser() {
        User user = new User();
        user.setUid(99);
        user.setFirstName("Makia");
        user.setLastName("Henry");
        underTest.create(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canGetAllUser() {
        underTest.list();
        verify(userRepository).findAll();
    }

    @Test
    void canGetUser() {
        Integer uid = 99;
        underTest.get(uid);

        ArgumentCaptor<Integer> uidArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userRepository).findByUid(uidArgumentCaptor.capture());

        Integer capturedUid = uidArgumentCaptor.getValue();
        assertThat(capturedUid).isEqualTo(uid);
    }

    @Test
    @Disabled
    void canUpdateUserWithUid() {
    }

    @Test
    @Disabled
    void canDeleteUser() {
    }
}