package com.example.rest_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(transactionService.geAllTransactions(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction){
        List<String> validationData = transactionService.addTransaction(transaction);
        String condition = validationData.removeLast();
        String responseBody = String.join(",",validationData);
        if(condition.equals("true")){
            return new ResponseEntity<>("Transaction was added successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable int id){
        Transaction transaction = transactionService.getTransaction(id);
        if(transaction != null)
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable LocalDate date){
        Transaction transaction = transactionService.getTransaction(date);
        if(transaction != null)
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable int id, @RequestBody Transaction updatedTransaction){
        boolean transactionUpdated = transactionService.transactionUpdate(id, updatedTransaction);
        if (transactionUpdated)
            return new ResponseEntity<>("Transaction updated succesfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Transaction not updated",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id){
        boolean transactionDeleted = transactionService.deleteTransaction(id);
        if(transactionDeleted)
            return new ResponseEntity<>("Transaction deleted",HttpStatus.OK);
        else
            return new ResponseEntity<>("Transaction was not deleted",HttpStatus.NOT_FOUND);
    }
}
