package com.example.rest_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TransactionController {
    private TransactionService transactionService;
    public TransactionController(){
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(){
        return transactionService.geAllTransactions();
    }

    @PostMapping("/transactions")
    public String addTransaction(@RequestBody Transaction transaction){
        transactionService.addTransaction(transaction);
        return("Transaction added succesfully");
    }

}
