package com.eqima.eqimaid.repository;

import com.eqima.eqimaid.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUid(String uid);
    void deleteByUid(String uid);
    Integer getCountByUid(String uid);

}
