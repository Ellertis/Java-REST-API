package com.example.rest_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    //@Mock
    TransactionService testTransactionService;

    IdEncoder encoder = new IdEncoder();
    TransactionMapperTrue mapper;

    //@InjectMocks
    TransactionController testTransactionController;

    TransactionRequest testRequest;// = new TransactionRequest(LocalDate.now(),1,"Emils");
    TransactionRequest testRequestUpdate;// = new TransactionRequest(LocalDate.now(),10,"Emils");
    TransactionResponse testResponse;// = new TransactionResponse("VFJTXzE=",LocalDate.now(),1,"Emils");
    TransactionResponse testResponseUpdate;// = new TransactionResponse("VFJTXzE=",LocalDate.now(),10,"Emils");
    List<TransactionResponse> testTransactionList = new ArrayList<TransactionResponse>();

    TransactionControllerTest() {
    }

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    private void setupForIntegrationTest(){
        // Initialize the real service with dependencies
        testTransactionService = new TransactionService(mapper,encoder);

        // Inject real service into controller
        testTransactionController = new TransactionController(testTransactionService);

        // Prepare test request
        testRequest = new TransactionRequest(LocalDate.now(), 1, "Emils");
    }

    private  void setupForTestingController(){
        testTransactionService = mock(TransactionService.class);
        testTransactionController = new TransactionController(testTransactionService);
    }

    private void setupForTestingService(){
        testTransactionService = new TransactionService(mapper,encoder);
        testTransactionController = mock(TransactionController.class);
    }

    private void peakMockery(){
        testTransactionService = mock(TransactionService.class);
        testTransactionController = mock(TransactionController.class);
    }

    @Test
    void runAllTests(){
        getAllTransactionsThroughController();
        addTransaction();
        IntegrationTest();
        getTransaction();
        updateTransaction();
        deleteTransaction();
    }

    @Test
    void getAllTransactionsThroughController(){
        setupForTestingController();

        when(testTransactionService.getAllTransactions()).thenReturn(testTransactionList);

        ResponseEntity<List<TransactionResponse>> response = testTransactionController.getAllTransactions();

        assertEquals(HttpStatus.OK,response.getStatusCode());

        verify(testTransactionService).getAllTransactions();
    }

    @Test
    void addTransaction(){
        setupForTestingController();

        when(testTransactionService.addTransaction(testRequest)).thenReturn(testResponse);

        ResponseEntity<TransactionResponse> response = testTransactionController.addTransaction(testRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testResponse, response.getBody());

        verify(testTransactionService).addTransaction(testRequest);
    }

    @Test //Adding a transaction, then modifying it
    void IntegrationTest(){
        setupForIntegrationTest();

        //Call the real controller method
        ResponseEntity<TransactionResponse> responseAdd = testTransactionController.addTransaction(testRequest);

        //Assertions on the response
        assertNotNull(responseAdd);
        assertEquals(HttpStatus.OK, responseAdd.getStatusCode());
        assertNotNull(responseAdd.getBody());
        assertEquals(testRequest.getName(), responseAdd.getBody().getName());
        assertEquals(testRequest.getAmount(), responseAdd.getBody().getAmount());

        //Now verify the transaction is stored in the service
        List<TransactionResponse> transactions = testTransactionService.getAllTransactions();
        assertEquals(1, transactions.size());

        TransactionResponse stored = transactions.getFirst();
        assertEquals(testRequest.getName(), stored.getName());
        assertEquals(testRequest.getAmount(), stored.getAmount());
        assertEquals(testRequest.getDate(),stored.getDate());
        System.out.println(testTransactionController.getAllTransactions().toString());

        //Modifying the request & Sending it
        testRequest.setAmount(10);
        testRequest.setName("Amadou");
        ResponseEntity<TransactionResponse> responseUpdate = testTransactionController.updateTransaction(stored.getPublicId(),testRequest);

        //Assertions on the update
        assertNotNull(responseUpdate);
        assertEquals(HttpStatus.OK,responseUpdate.getStatusCode());
        assertNotNull(responseUpdate.getBody());
        assertEquals(testRequest.getAmount(),responseUpdate.getBody().getAmount());
        assertEquals(testRequest.getName(),responseUpdate.getBody().getName());

        //Update list
        transactions = testTransactionService.getAllTransactions();
        assertEquals(1, transactions.size());
        stored = transactions.getFirst();

        //Assertions on the transaction that saved inside the service
        assertEquals(testRequest.getName(), stored.getName());
        assertEquals(testRequest.getAmount(), stored.getAmount());
        System.out.println(testTransactionController.getAllTransactions().toString());
    }

    @Test
    void getTransaction() {
        setupForTestingController();

        String publicId = "VFJTXzE="; //VFJTXzE= == TRS_1 in base64
        testRequest = new TransactionRequest(LocalDate.now(),1000,"Amadou");
        testResponse = new TransactionResponse(publicId,testRequest.getDate(),testRequest.getAmount(),testRequest.getName());

        when(testTransactionService.getTransaction(publicId)).thenReturn(testResponse);

        ResponseEntity<TransactionResponse> testTransactionResponse = testTransactionController.getTransaction(publicId);

        assertEquals(HttpStatus.OK,testTransactionResponse.getStatusCode());
        assertEquals(testResponse,testTransactionResponse.getBody());

        verify(testTransactionService).getTransaction(publicId);
    }

    @Test
    void updateTransaction() {
        setupForTestingController();

        String publicId = "VFJTXzE="; //VFJTXzE= == TRS_1 in base64
        testRequest = new TransactionRequest(LocalDate.now(),1000,"Amadou");
        testResponse = new TransactionResponse(publicId,testRequest.getDate(),testRequest.getAmount()+1,testRequest.getName());

        when(testTransactionService.transactionUpdate(publicId,testRequest)).thenReturn(testResponse);

        ResponseEntity<TransactionResponse> testTransactionResponse = testTransactionController.updateTransaction(publicId,testRequest);

        assertNotNull(testTransactionResponse);
        assertEquals(HttpStatus.OK,testTransactionResponse.getStatusCode());
        assertEquals(testResponse,testTransactionResponse.getBody());

        verify(testTransactionService).transactionUpdate(publicId,testRequest);

        System.out.println("Passed: UpdateTransaction");
    }

    @Test
    void deleteTransaction() {
        setupForTestingController();

        doNothing().when(testTransactionService).deleteTransaction("VFJTXzE=");

        ResponseEntity<Void> response = testTransactionController.deleteTransaction("VFJTXzE=");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(testTransactionService).deleteTransaction("VFJTXzE=");

        System.out.println("Passed: Delete Transaction");
    }
}