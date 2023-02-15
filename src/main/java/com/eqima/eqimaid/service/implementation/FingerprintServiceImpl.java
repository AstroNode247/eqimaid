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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class FingerprintServiceImpl implements FingerprintService {
    private final FingerprintRepository fingerprintRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final String DATA_PATH = "E:/Projects/Web/eqimaid/data/";

//    @Override
//    public Fingerprint addFingerprint(String ownerId, Fingerprint fingerprint) {
//        log.info("Add fingerprint");
//        fingerprint.setPath(setFingerprintPath(ownerId, fingerprint.getPath()));
//        Fingerprint _fingerprint = fingerprintRepository.save(fingerprint);
//
//        mongoTemplate.update(User.class)
//                .matching(Criteria.where("uid").is(ownerId))
//                .apply(new Update().push("fingerprintId").value(fingerprint))
//                .first();
//
//        return _fingerprint;
//    }

    @Override
    public Fingerprint addFingerprint(String ownerId, MultipartFile file) throws IOException {
        log.info("Add fingerprint");
        User user = userRepository.findByUid(ownerId);
        int nbFinger = user.getFingerprintId().size();
        String[] filename = file.getOriginalFilename().split("\\.");
        String lastName = filename[0] + "_" + nbFinger;

        String imgPath = DATA_PATH + ownerId + "/" + lastName;
        File filePath = new File(imgPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        file.transferTo(filePath);

        Fingerprint fingerprint = fingerprintRepository.save(Fingerprint.builder()
                .name(file.getOriginalFilename())
                .path(setFingerprintPath(ownerId, lastName)).build());

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
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/fingerprint/" + ownerId + "/" + filename)
                .toUriString();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String filePath = DATA_PATH + file.getOriginalFilename();
        Fingerprint fingerprint = fingerprintRepository.save(Fingerprint.builder()
                .name(file.getOriginalFilename())
                .path(filePath).build());

        file.transferTo(new File(filePath));
        return "Fingerprint uploaded successfully : " + filePath;
    }

    public byte[] downloadImage(String filename) throws IOException {
        Optional<Fingerprint> fingerprint = fingerprintRepository.findByName(filename);
        String filePath = fingerprint.get().getPath();
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
