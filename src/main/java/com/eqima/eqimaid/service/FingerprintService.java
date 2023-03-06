package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.Fingerprint;

import java.io.IOException;
import java.util.Collection;

public interface FingerprintService {

    Fingerprint addFingerprint(String ownerId, String filename) throws IOException;

    Collection<Fingerprint> list();

}
