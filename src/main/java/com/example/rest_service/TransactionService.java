package com.example.rest_service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();

    public TransactionService(){
        transactions.add(new Transaction(1, LocalDate.now(),10));
    }
    public List<Transaction> geAllTransactions(){
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        return;
    }
}
