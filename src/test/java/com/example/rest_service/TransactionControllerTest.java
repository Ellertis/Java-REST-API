package com.example.rest_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {

    private TransactionController transactionController;

    TransactionControllerTest() {
    }

    @BeforeEach
    public void setup(){
        //transactionController = new TransactionController(new TransactionService());
    }

    @Test
    void getAllTransactions(){
        /*
        ResponseEntity<String> responseEntity = transactionController.addTransaction(new Transaction(1, LocalDate.now(),10,"EmilsTest"));
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        System.out.println(responseEntity);

        List<Transaction> transactions = transactionController.getAllTransactions().getBody();
        Assertions.assertNotNull(transactions);
        System.out.println(transactions);

         */
        //Mockito.when(transactionController.getAllTransactions()).thenReturn(List<Transaction>));

    }

    @Test
    void addTransaction() {
    }

    @Test
    void getTransaction() {
    }

    @Test
    void testGetTransaction() {
    }

    @Test
    void updateTransaction() {
    }

    @Test
    void deleteTransaction() {
    }
}