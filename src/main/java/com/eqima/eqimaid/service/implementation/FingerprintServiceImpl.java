package com.eqima.eqimaid.service.implementation;

import com.eqima.eqimaid.model.Fingerprint;
import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.repository.FingerprintRepository;
import com.eqima.eqimaid.repository.UserRepository;
import com.eqima.eqimaid.service.FingerprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Collection;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class FingerprintServiceImpl implements FingerprintService {
    private final FingerprintRepository fingerprintRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Fingerprint addFingerprint(String ownerId, String filename) throws IOException {
        log.info("Add fingerprint");
        User user = userRepository.findByUid(ownerId);

        Fingerprint fingerprint = fingerprintRepository.save(Fingerprint.builder()
                .name(filename)
                .path(setFingerprintPath(ownerId, filename)).build());

        mongoTemplate.update(User.class)
                .matching(Criteria.where("uid").is(ownerId))
                .apply(new Update().push("fingerprintId").value(fingerprint))
                .first();

        return fingerprint;
    }

    @Override
    public Collection<Fingerprint> list() {
        log.info("Fetch all fingerprints");
        return fingerprintRepository.findAll();
    }

    private String setFingerprintPath(String ownerId, String filename) {
        String recognizerEndpoint = "http://127.0.0.1:5000/v1/fingerprint/";
        return recognizerEndpoint + ownerId + "/" + filename;
    }
}
