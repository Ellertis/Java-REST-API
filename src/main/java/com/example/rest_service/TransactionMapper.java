package com.example.rest_service;

import com.example.rest_service.MongoDB.EntityToSave;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.swing.text.html.parser.Entity;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionRequest request);

    Transaction toEntity(EntityToSave entity);

    TransactionResponse toResponse(Transaction entity);

    TransactionResponse toResponse(EntityToSave entity);

    @Mapping(target = "mongoId", ignore = true)
    EntityToSave toSaveEntity(Transaction entity);

    //public void updateEntityFromRequest(TransactionRequest request, Transaction entity);
    @AfterMapping
    default Transaction updateEntityFromRequest(TransactionRequest request,@MappingTarget Transaction entity){
        entity.setDate(request.getDate());
        entity.setAmount(request.getAmount());
        entity.setName(request.getName());
        return entity;
    }
}