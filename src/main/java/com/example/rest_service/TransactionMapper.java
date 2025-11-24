package com.example.rest_service;

import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private static IdEncoder idEncoder;

    public TransactionMapper(IdEncoder idEncoder) {
        this.idEncoder = idEncoder;
    }

    public Transaction toEntity(TransactionRequest request) {
        Transaction entity = new Transaction();
        entity.setDate(request.getDate());
        entity.setAmount(request.getAmount());
        entity.setName(request.getName());
        return entity;
    }

    public TransactionResponse toResponse(Transaction entity) {
        TransactionResponse response = new TransactionResponse();
        response.setPublicId(idEncoder.encode(entity.getId()));
        response.setDate(entity.getDate());
        response.setAmount(entity.getAmount());
        response.setName(entity.getName());
        return response;
    }

    public void updateEntityFromRequest(TransactionRequest request, Transaction entity) {
        entity.setDate(request.getDate());
        entity.setAmount(request.getAmount());
        entity.setName(request.getName());
    }
}