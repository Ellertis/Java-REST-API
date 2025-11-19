package com.example.rest_service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private int id;
    private LocalDate date;
    private int amount;
    private String name;

    public Transaction(int id, LocalDate date, int amount,String name) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.name = name;
    }
}