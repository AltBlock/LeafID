package com.surya.loginTest.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.surya.loginTest.model.AppUser;



public interface UserRepository extends MongoRepository<AppUser, String> {
	AppUser findByEmail(String email);

}
