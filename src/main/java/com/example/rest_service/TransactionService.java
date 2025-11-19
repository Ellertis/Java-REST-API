package com.example.rest_service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    public TransactionService(){
    }

    public List<Transaction> getAllTransactions(){
        return transactions;
    }

    public List<String> addTransaction(Transaction transaction) {
        try {
            if (transactionDataValidation(transaction).getLast().equals("true")){
                transaction.setId(nextId++);
                transactions.add(transaction);
                saveTransaction(transaction, "added successfully");
                return transactionDataValidation(transaction);}}
        catch(Exception e){
                return transactionDataValidation(transaction);
            }
        return transactionDataValidation(transaction);
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
            saveTransaction(transaction,"updated successfully");
        return true;}
        catch (Exception e) {
            return false;}
    }

    public boolean deleteTransaction(int id){
        if(transactions.removeIf(transaction -> transaction.getId() == id))
            try{
            saveTransaction(id,"deleted successfully");
            return true;}
        catch (Exception e){
                return  false;
        }
        else
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
        boolean safe = true;

        if(transaction.getDate().isBefore(LocalDate.now())) {
            errorCode.add("The transaction is dated for the day before");
            safe = false;
        }

        if(transaction.getName() == null || transaction.getName().trim().isEmpty()){
            errorCode.add("Name is formated incorrectly");
            safe = false;
        }

        if(transaction.getAmount() <= 0){
            errorCode.add("Invalid Amount below or equal zero");
            safe = false;
        }

        //LAST
        errorCode.add(String.valueOf(safe));
        return errorCode;
    }

    public static void saveTransaction(Transaction transaction,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("TransactionActionsLog"+".txt",true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+ transaction.getId()+" Date: "+transaction.getDate()+" Amount: "+transaction.getAmount());
        writer.close();
    }
    public static void saveTransaction(int id,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("TransactionActionsLog"+".txt",true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+id);
        writer.close();
    }
}