package com.example.rest_service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    public TransactionService(){
    }

    public List<Transaction> geAllTransactions(){
        return transactions;
    }

    public boolean addTransaction(Transaction transaction){
        try{
            transaction.setId(nextId++);
            transactions.add(transaction);
            return true;}
        catch (Exception e){
            return false;
        }
    }

    public Transaction getTransaction(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id)
                return transaction;
            }
        return null;
    }

    public Transaction getTransaction(LocalDate date) {
        for (Transaction transaction : transactions) {
            if (transaction.getDate().equals(date))
                return transaction;
        }
        return null;
    }

    public boolean transactionUpdate(int id, Transaction updatedTransaction) {
        Transaction transaction = getTransaction(id);
        try{
        transaction.setDate(updatedTransaction.getDate());
        transaction.setAmount(updatedTransaction.getAmount());
        return true;}
        catch (Exception e) {
            return false;}
    }

    public boolean deleteTransaction(int id){
        return transactions.removeIf(transaction -> transaction.getId() == id);
        /* OR
        Transaction transaction = getTransaction(id);
        try{
            transactions.remove(transaction);
            return true;}
        catch (Exception e) {
            return false;}

         */
    }
}