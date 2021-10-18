package com.marketplace.Authentication.repositories;

import com.marketplace.Authentication.models.UserAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationRepository extends MongoRepository<UserAuthentication, String> {

    List<UserAuthentication> findByUserId(String userId);

}
