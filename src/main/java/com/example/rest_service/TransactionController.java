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
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction AddedTransaction = transactionService.addTransaction(transaction);
            return ResponseEntity.ok("Transaction was added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable int id){
        Transaction transaction = transactionService.getTransaction(id);
        return transaction !=null ?
                ResponseEntity.ok(transaction) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable LocalDate date){
        Transaction transaction = transactionService.getTransaction(date);
        return transaction !=null ?
            ResponseEntity.ok(transaction) :
            ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable int id, @RequestBody Transaction updatedTransaction){
        boolean transactionUpdated = transactionService.transactionUpdate(id, updatedTransaction);
        return transactionUpdated ?
            ResponseEntity.ok("Transaction updated successfully") :
            ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id){
        boolean transactionDeleted = transactionService.deleteTransaction(id);
        return transactionDeleted ?
            ResponseEntity.ok("Transaction deleted") :
            ResponseEntity.notFound().build();
    }
}
