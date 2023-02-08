package com.eqima.eqimaid.service;

import com.eqima.eqimaid.repository.FingerprintRepository;
import com.eqima.eqimaid.service.implementation.FingerprintServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class FingerprintServiceTest {
    @Mock
    private FingerprintRepository fingerprintRepository;
    @Mock
    private MongoTemplate mongoTemplate;
    private FingerprintServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new FingerprintServiceImpl(fingerprintRepository, mongoTemplate);
    }

    @Test
    void canGetAllFingerprints() {
        underTest.list();
        verify(fingerprintRepository).findAll();
    }

    @Test
    @Disabled
    void addFingerprint() {
    }
}