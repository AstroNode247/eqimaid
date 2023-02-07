package com.eqima.eqimaid.service;

import com.eqima.eqimaid.model.Fingerprint;

import java.util.Collection;

public interface FingerprintService {
    Fingerprint addFingerprint(Integer ownerId, Fingerprint fingerprint);

    Collection<Fingerprint> list();
}
