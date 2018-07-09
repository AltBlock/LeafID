package com.surya.loginTest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.surya.loginTest.model.Plant;



public interface PlantRepository extends MongoRepository<Plant, String> {

}
