package com.example.rest_service.MongoDB;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "entities")
public class EntityToSave {
    @Id
    private String MongoId;

    @Indexed(unique = true)
    private String publicId;

    @Field(name = "transaction_name")
    private String name;

    @Field(name = "transaction_amount")
    private float amount;

    public EntityToSave(String name,float amount){
        this.name = name;
        this.amount = amount;
    }

}

