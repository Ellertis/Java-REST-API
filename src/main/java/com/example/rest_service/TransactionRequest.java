package com.example.rest_service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private LocalDate date;
    private int amount;
    private String name;

    public TransactionRequest(){}

    public TransactionRequest(LocalDate date, int amount, String name) {
        this.date = date;
        this.amount = amount;
        this.name = name;
    }

}
