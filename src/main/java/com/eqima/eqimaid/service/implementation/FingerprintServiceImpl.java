package com.eqima.eqimaid.service.implementation;

import com.eqima.eqimaid.model.Fingerprint;
import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.repository.FingerprintRepository;
import com.eqima.eqimaid.service.FingerprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class FingerprintServiceImpl implements FingerprintService {
    private final FingerprintRepository fingerprintRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Fingerprint addFingerprint(Integer ownerId, Fingerprint fingerprint) {
        log.info("Add fingerprint");
        fingerprint.setPath(setFingerprintPath(fingerprint.getPath()));
        Fingerprint _fingerprint = fingerprintRepository.save(fingerprint);

        mongoTemplate.update(User.class)
                .matching(Criteria.where("uid").is(ownerId))
                .apply(new Update().push("fingerprintId").value(fingerprint))
                .first();

        return _fingerprint;
    }

    @Override
    public Collection<Fingerprint> list() {
        log.info("Fetch all fingerprints");
        return fingerprintRepository.findAll();
    }

    private String setFingerprintPath(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/fingerprint/" + filename).toUriString();
    }
}
