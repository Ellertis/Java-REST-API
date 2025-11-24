package com.example.rest_service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponse {
    private String publicId;
    private LocalDate date;
    private int amount;
    private String name;

    public TransactionResponse(){}

    public TransactionResponse(String publicId, LocalDate date, int amount, String name) {
        this.publicId = publicId;
        this.date = date;
        this.amount = amount;
        this.name = name;
    }
}
