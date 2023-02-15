package com.eqima.eqimaid.repository;

import com.eqima.eqimaid.model.Fingerprint;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FingerprintRepository extends MongoRepository<Fingerprint, ObjectId> {
    Optional<Fingerprint> findByName(String filename);
}
