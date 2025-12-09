package com.example.rest_service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionRequest request);

    TransactionResponse toResponse(Transaction entity);

    //public void updateEntityFromRequest(TransactionRequest request, Transaction entity);
    @AfterMapping
    default Transaction updateEntityFromRequest(TransactionRequest request,@MappingTarget Transaction entity){
        entity.setDate(request.getDate());
        entity.setAmount(request.getAmount());
        entity.setName(request.getName());
        return entity;
    }
}