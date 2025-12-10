package com.example.rest_service.MongoDB;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoTestRepository extends MongoRepository<EntityToSave, String> {
    public EntityToSave findByName(String name);
    public List<EntityToSave> findByAmount(float amount);
    public EntityToSave findByPublicId (String id);
}

