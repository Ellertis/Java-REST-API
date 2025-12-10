package com.example.rest_service.MongoDB;

import com.example.rest_service.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
@Configuration
public class TestClass implements CommandLineRunner {

    @Autowired
    MongoTestRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TestClass.class, args);
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

    public void saveDB(EntityToSave entity) throws IOException {
        repository.save(entity);
        System.out.println(repository.findAll().getLast());
        if(repository.findByPublicId(entity.getPublicId()) == null)
            throw new IOException("MongoDB isn't mongoDBing");
    }

    /*
    Optional <EntityToSave> updatedEntity = mongoTestRepository.findById(entity.getId());
    assert updatedEntity.isPresent();
    assert updatedEntity.equals(mongoTestRepository.findById(entity.getId()));
    System.out.println(mongoTestRepository.count());
    */
}
