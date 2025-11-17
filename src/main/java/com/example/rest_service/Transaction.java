package com.example.rest_service;

import java.time.LocalDate;

public class Transaction {
    private float id;
    private LocalDate date;
    private float amount;

    public Transaction(float id, LocalDate date, float amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
