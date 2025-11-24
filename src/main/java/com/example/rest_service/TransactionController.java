package com.example.rest_service;

import com.example.rest_service.Exceptions.TransactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> addTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.addTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String publicId) {
        TransactionResponse response = transactionService.getTransaction(publicId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable LocalDate date) {
        Transaction transaction = transactionService.getTransaction(date);
        return transaction != null ?
                ResponseEntity.ok(transactionService.transactionMapper.toResponse(transaction)) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable String publicId, @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.transactionUpdate(publicId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String publicId) {
        try {
            transactionService.deleteTransaction(publicId);
            return ResponseEntity.ok().build();
        } catch (TransactionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}