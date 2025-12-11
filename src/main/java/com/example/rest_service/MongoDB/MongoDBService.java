package com.example.rest_service.MongoDB;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MongoDBService/* implements CommandLineRunner*/ {

    @Autowired
    MongoDBRepo repository;

    @PostConstruct
    public void init(){
        repository.deleteAll();
    }
/*
    public static void main(String[] args) {
        SpringApplication.run(MongoDBService.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        repository.deleteAll();
        System.out.println("Doing things");

        EntityToSave entity1 = new EntityToSave("Emils",10);
        EntityToSave entity2 = new EntityToSave("Amadou",10);

        //repository.save(entity1);
        repository.save(entity2);
        System.out.println(repository.findAll());
    }
*/
    public void saveDB(EntityToSave entity){
        System.out.println("Before: " + repository.count());
        repository.save(entity);
        System.out.println("After: " + repository.count());
        System.out.println(repository.findAll().getLast());
        System.out.println(repository.findAll());
        System.out.println(entity.getMongoId().toString());
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

    /*
    Optional <EntityToSave> updatedEntity = mongoTestRepository.findById(entity.getId());
    assert updatedEntity.isPresent();
    assert updatedEntity.equals(mongoTestRepository.findById(entity.getId()));
    System.out.println(mongoTestRepository.count());
    */
}
