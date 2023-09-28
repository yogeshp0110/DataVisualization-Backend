package com.maveric.datavisualization.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maveric.datavisualization.controllers.TransactionController;
import com.maveric.datavisualization.dtos.TransactionDetails;
import com.maveric.datavisualization.entities.Transaction;
import com.maveric.datavisualization.services.TransactionService;

import jakarta.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTxns() throws JsonProcessingException {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionService.getAllTxns()).thenReturn(transactions);
        ResponseEntity<List<Transaction>> response = transactionController.getAllTxns();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    public void testGetTransactions() throws JsonProcessingException {
        long txnId = 1L;
        Transaction transaction = new Transaction();
        when(transactionService.getTransactions(txnId)).thenReturn(transaction);
        ResponseEntity<Transaction> response = transactionController.getTransactions(txnId, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }
    @Test
    public void testDeleteTxnSuccess() throws JsonProcessingException {
        long txnId = 1L;
        doNothing().when(transactionService).deleteTxn(txnId);
        ResponseEntity<String> response = transactionController.deleteTxn(txnId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction deleted successfully!", response.getBody());
        verify(transactionService, times(1)).deleteTxn(txnId);
    }

    @Test
    public void testUpdateTxnSuccess() throws JsonProcessingException {
        long txnId = 1L;
        TransactionDetails transactionDetails = new TransactionDetails();
        Transaction updatedTransaction = new Transaction();
        when(transactionService.updateTxn(eq(txnId), any(TransactionDetails.class))).thenReturn(updatedTransaction);
        ResponseEntity<Transaction> response = transactionController.updateTxn(txnId, transactionDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTransaction, response.getBody());
        verify(transactionService, times(1)).updateTxn(eq(txnId), any(TransactionDetails.class));
    }

}

