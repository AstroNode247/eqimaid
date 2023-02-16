package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.Fingerprint;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

public interface FingerprintService {
//    Fingerprint addFingerprint(String ownerId, Fingerprint fingerprint);
    String uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(String filename) throws IOException;

    Fingerprint addFingerprint(String ownerId) throws IOException;

    Collection<Fingerprint> list();
}
