package com.example.rest_service;

import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

public class ServiceUnitTest {

    @Test
    void test(){
        TransactionService mockService = Mockito.mock(TransactionService.class);
        Transaction t = new Transaction(1, LocalDate.now(),10,"EmilsTest");
        when(mockService.addTransaction(t));
        when(mockService.getTransaction(1)).thenReturn(t);
        //idk how to implement the test

    }
}
