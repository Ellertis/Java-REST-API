package com.example.rest_service.MongoDB;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MongoDBService{

    @Autowired
    MongoDBRepo repository;

    public MongoDBService(){}

    public MongoDBService(MongoDBRepo mongoDBRepo){
        this.repository = mongoDBRepo;
    }

    @PostConstruct
    public void init(){
        repository.deleteAll();
        //repository put logic here
    }

    public void saveTransaction(EntityToSave entity){
        System.out.println("Before: " + repository.count());
        repository.save(entity);
        System.out.println("After: " + repository.count());
        System.out.println(repository.findAll().getLast());
        System.out.println(repository.findAll());
    }

    private void delete(){
        repository.deleteAll();
    }

    public void delete(EntityToSave entity){
        repository.delete(entity);
    }

    public EntityToSave getLastTransaction(){
        return repository.findAll().getLast();
    }

    public EntityToSave getTransactionById(int id){
        return repository.findById(id);
    }
    public EntityToSave getTransactionByDate(LocalDate date){
        return repository.findByDate(date);
    }

    public List<EntityToSave> getAllTransactions(){
        return repository.findAll().stream().toList();
    }

    public long getCount(){
        return repository.count();
    }

    public void updateTransaction(EntityToSave entity){
        repository.updateById(entity.getId(), entity.getName(), entity.getAmount(),entity.getDate());
    }
}
