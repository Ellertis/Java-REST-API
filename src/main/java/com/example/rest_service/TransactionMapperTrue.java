package com.example.rest_service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TransactionMapperTrue {

    Transaction toEntity(TransactionRequest request);

    TransactionResponse toResponse(Transaction entity);

    //public void updateEntityFromRequest(TransactionRequest request, Transaction entity);
    @AfterMapping
    default void updateEntityFromRequest(TransactionRequest request,@MappingTarget Transaction entity){
        entity.setDate(entity.getDate());
        entity.setAmount(entity.getAmount());
        entity.setName(entity.getName());
    }
}