package com.example.rest_service.MongoDB;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "entities")
public class EntityToSave {
    @Id
    private ObjectId mongoId;

    @Field(name = "internal_id")
    private int id;

    @Indexed(unique = true)
    private String publicId;

    @Field(name = "transaction_name")
    private String name;

    @Field(name = "transaction_amount")
    private int amount;

    @Field(name = "date")
    private LocalDate date;

    public EntityToSave(String name,int amount){
        this.name = name;
        this.amount = amount;
    }

}

