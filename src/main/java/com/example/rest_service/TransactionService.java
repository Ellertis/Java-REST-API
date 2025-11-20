package com.example.rest_service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionService {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionService() {
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Transaction addTransaction(Transaction transaction) {
        List<String> errors = transactionDataValidation(transaction);
        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join(", ",errors));
        }

        transaction.setId(nextId.getAndAdd(1));
        transactions.add(transaction);

        try{
            saveTransaction(transaction, "added successfully");}
        catch (Exception e){
            System.out.println("Failed to log transaction creation: " + e.getMessage());
        }
        return transaction;
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
        if (transaction == null){
            return false;
        }

        transaction.setDate(updatedTransaction.getDate());
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setName(updatedTransaction.getName());

        try{
        saveTransaction(transaction,"updated successfully");
        return true;}
        catch (Exception e) {
            System.out.println("Failed to log transaction creation: " + e.getMessage());
            return false;}
    }

    public boolean deleteTransaction(int id){
        if(transactions.removeIf(transaction -> transaction.getId() == id)) {
            try{
            saveTransaction(id,"deleted successfully");
            return true;}
        catch (Exception e){
            System.out.println("Failed to log transaction creation: " + e.getMessage());
            return true;}
        }
        return false;
        /* OR
        without logging : return transactions.removeIf(transaction -> transaction.getId() == id;
        OR
        Transaction transaction = getTransaction(id);
        try{
            transactions.remove(transaction);
            saveTransaction(transaction,"deleted successfully");
            return true;}
        catch (Exception e) {
            return false;}
         */
    }

    public static List<String> transactionDataValidation(Transaction transaction){
        List<String> errorCode = new ArrayList<>();

        if(transaction.getDate().isBefore(LocalDate.now())) {
            errorCode.add("The transaction is dated for the past");
        }

        if(transaction.getName() == null || transaction.getName().trim().isEmpty()){
            errorCode.add("Name is formated incorrectly");
        }

        if(transaction.getAmount() <= 0){
            errorCode.add("Invalid Amount below or equal zero");
        }

        //LAST
        return errorCode;
    }

    public static void saveTransaction(Transaction transaction,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("TransactionActionsLog"+".txt",true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+ transaction.getId()+" Date: "+transaction.getDate()+" Amount: "+transaction.getAmount()+" Name: "+transaction.getName());
        writer.close();
    }
    public static void saveTransaction(int id,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("TransactionActionsLog"+".txt",true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+id);
        writer.close();
    }
}