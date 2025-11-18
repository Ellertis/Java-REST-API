package com.example.rest_service;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private LocalDate date;
    private int amount;

    public Transaction(int id, LocalDate date, int amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
