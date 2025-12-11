package com.example.rest_service.MongoDB;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.LocalDate;
import java.util.List;


public interface MongoDBRepo extends MongoRepository<EntityToSave, ObjectId>{
    public List<EntityToSave> findByName(String name);
    public List<EntityToSave> findByAmount(float amount);
    public EntityToSave findByPublicId (String id);
    public EntityToSave findById(int id);
    public EntityToSave findByDate(LocalDate date);

    @Query("{'id': ?0}")
    @Update("{$set: { 'name': ?1, 'amount': ?2, 'date': ?3 }}")
    void updateById(int id,String name, int amount, LocalDate date);
}