package com.example.rest_service;

import com.example.rest_service.Exceptions.TransactionNotFoundException;
import com.example.rest_service.Exceptions.TransactionValidationException;
import com.example.rest_service.MongoDB.MongoDBService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private AtomicInteger nextId;// = new AtomicInteger(0);
    private final List<Transaction> transactions = new ArrayList<>();
    private final TransactionMapper transactionMapper;

    @Autowired
    private MongoDBService mongoDBService;

    //@Value("${app.logging.file}")
    private final String logFile = ("test.txt");

    public TransactionService(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @PostConstruct
    public void init(){
        System.out.println(mongoDBService.getCount());
        nextId = (mongoDBService.getCount() == 0)? new AtomicInteger(0)
                :new AtomicInteger(IdEncoder.decode(mongoDBService.getLastTransaction().getPublicId())+1);
    }

    public List<TransactionResponse> getAllTransactions() {
        return transactions.stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getAllTransactionsDB(){
        return mongoDBService.getAllTransactions().stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse addTransaction(TransactionRequest request) {
        List<String> errors = transactionDataValidation(request);
        if (!errors.isEmpty()) {
            throw new TransactionValidationException(String.join(", ",errors));
        }

        Transaction transaction = transactionMapper.toEntity(request);
        transaction.setId(nextId.getAndAdd(1));
        transaction.setPublicId(IdEncoder.encode(transaction.getId()));
        transactions.add(transaction);

        try{
            saveTransaction(transaction, "added successfully");
            mongoDBService.saveTransaction(transactionMapper.toSaveEntity(transaction));
        }
        catch (Exception e){
            System.out.println("Failed to log transaction creation: " + e.getMessage());
        }
        return transactionMapper.toResponse(transaction);
    }

    public TransactionResponse getTransaction(String publicId) {
        int internalId = IdEncoder.decode(publicId);
        Transaction transaction = findTransactionById(internalId);
        transaction = transactionMapper.toEntity(mongoDBService.getTransactionById(internalId));
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction not found with ID: " + publicId);
        }
        return transactionMapper.toResponse(transaction);
    }

    public TransactionResponse getTransaction(LocalDate date) {
        return transactionMapper.toResponse(mongoDBService.getTransactionByDate(date));

        /*
        for (Transaction transaction : transactions) {
            if (transaction.getDate().equals(date))
                return transactionMapper.toResponse(transaction);
        }
        return null;
         */
    }

    public TransactionResponse transactionUpdate(String publicId, TransactionRequest request) {
        List<String> errors = transactionDataValidation(request);
        if (!errors.isEmpty()) {
            throw new TransactionValidationException(String.join(", ",errors));
        }

        int internalId = IdEncoder.decode(publicId);
        Transaction transaction = findTransactionById(internalId);
        transaction = transactionMapper.toEntity(mongoDBService.getTransactionById(internalId));

        if (transaction == null){
            throw new TransactionNotFoundException("Transaction not found with ID: "+ publicId);
        }

        transactionMapper.updateEntityFromRequest(request,transaction);

        try{
            saveTransaction(transaction,"updated successfully");
            mongoDBService.updateTransaction(transactionMapper.toSaveEntity(transaction));
        }
        catch (Exception e) {
            System.out.println("Failed to log transaction creation: " + e.getMessage());}

        return  transactionMapper.toResponse(transaction);
    }

    public void deleteTransaction(String publicId){
        int internalId = IdEncoder.decode(publicId);
        mongoDBService.delete(mongoDBService.getTransactionById(internalId));
        if(transactions.removeIf(transaction -> transaction.getId() == internalId)) {
            try{
            saveTransaction(internalId,"deleted successfully");
            }
        catch (Exception e){
            System.out.println("Failed to log transaction deletion: " + e.getMessage());
        }

        }
        /* OR
        without logging : return transactions.removeIf(transaction -> transaction.getId() == id; BUT FUNCTION MUST RETURN BOOLEAN
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

    private static List<String> transactionDataValidation(TransactionRequest request){
        List<String> errorCode = new ArrayList<>();

        if(request.getDate().isBefore(LocalDate.now())) {
            errorCode.add("The transaction is dated for the past");
        }

        if(request.getName() == null || request.getName().trim().isEmpty()){
            errorCode.add("Name is formated incorrectly");
        }

        if(request.getAmount() <= 0){
            errorCode.add("Invalid Amount below or equal zero");
        }

        //LAST
        return errorCode;
    }

    private Transaction findTransactionById(int internalId) {
        return transactions.stream()
                .filter(t -> t.getId() == internalId)
                .findFirst()
                .orElse(null);
    }

    public void saveTransaction(Transaction transaction,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile,true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+ transaction.getId()+" Date: "+transaction.getDate()+" Amount: "+transaction.getAmount()+" Name: "+transaction.getName());
        writer.close();
    }
    public void saveTransaction(int id,String comment) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile,true));
        writer.newLine();
        writer.write("Transaction "+comment+" Id: "+id);
        writer.close();
    }
}