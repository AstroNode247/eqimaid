package com.eqima.eqimaid.repository;

import com.eqima.eqimaid.model.Fingerprint;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FingerprintRepository extends MongoRepository<Fingerprint, ObjectId> {
}
