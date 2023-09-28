package com.maveric.datavisualization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maveric.datavisualization.daos.TransactionRepo;
import com.maveric.datavisualization.dtos.TransactionDetails;
import com.maveric.datavisualization.entities.Transaction;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.services.TransactionServiceImpl;

public class TransationServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepo transactionRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactions_Positive() throws JsonProcessingException {
        Long txnId = 1L;
        Transaction transaction = new Transaction();
        transaction.setTxnId(txnId);
        when(transactionRepo.findById(txnId)).thenReturn(Optional.of(transaction));
        try {
            Transaction result = transactionService.getTransactions(txnId);
            assertNotNull(result);
            assertEquals(txnId, result.getTxnId());
        } catch (CustomExceptions e) {
            fail("Should not throw CustomExceptions");
        }
        verify(transactionRepo, times(1)).findById(txnId);
    }

    @Test
    public void testGetTransactions_Negative() {
        Long txnId = 1L;
        when(transactionRepo.findById(txnId)).thenReturn(Optional.empty());
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            transactionService.getTransactions(txnId);
        });
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertEquals("Transaction details not found", exception.getError());
        verify(transactionRepo, times(1)).findById(txnId);
    }

    @Test
    public void testGetAllTxns() throws JsonProcessingException {
        List<Transaction> expectedTransactions = Arrays.asList(
            new Transaction(152L,1111L,"c",90.34,"food billing","jhv","hvj","M","Success", null,null),
            new Transaction(152L,1111L,"c",90.34,"food billing","jhv","hvj","M","Success", null,null)
        );
        when(transactionRepo.findAll()).thenReturn(expectedTransactions);
        List<Transaction> actualTransactions = transactionService.getAllTxns();
        assertNotNull(actualTransactions);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
        assertEquals(expectedTransactions, actualTransactions);

        verify(transactionRepo, times(1)).findAll();
    }
    
    @Test
    public void testUpdateTxn_Positive() throws JsonProcessingException {
        Long txnId = 1L;
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnId(txnId);

        Transaction existingTransaction = new Transaction();
        existingTransaction.setTxnId(txnId);

        when(transactionRepo.findById(txnId)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepo.save(existingTransaction)).thenReturn(existingTransaction);

        try {
            Transaction result = transactionService.updateTxn(txnId, transactionDetails);
            assertNotNull(result);
            assertEquals(txnId, result.getTxnId());
        } catch (CustomExceptions e) {
            fail("Should not throw CustomExceptions");
        }

        verify(transactionRepo, times(1)).findById(txnId);
        verify(transactionRepo, times(1)).save(existingTransaction);
    }

    @Test
    public void testUpdateTxn_Negative_InvalidId() {
        Long txnId = 1L;
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnId(txnId);

        when(transactionRepo.findById(txnId)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            transactionService.updateTxn(txnId, transactionDetails);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertEquals(" Given Txn_Id:1in Request and txnId in requestData ::: 1is not same", exception.getError());

        verify(transactionRepo, times(1)).findById(txnId);
        verify(transactionRepo, never()).save(any());
    }

    @Test
    public void testDeleteTxn_Positive() throws JsonProcessingException {
        Long txnId = 1L;
        Transaction transaction = new Transaction();
        transaction.setTxnId(txnId);

        when(transactionRepo.findById(txnId)).thenReturn(Optional.of(transaction));

        try {
            transactionService.deleteTxn(txnId);
        } catch (CustomExceptions e) {
            fail("Should not throw CustomExceptions");
        }

        verify(transactionRepo, times(1)).findById(txnId);
        verify(transactionRepo, times(1)).deleteById(txnId);
    }

    @Test
    public void testDeleteTxn_Negative_NotFound() {
        Long txnId = 1L;
        when(transactionRepo.findById(txnId)).thenReturn(Optional.empty());

        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            transactionService.deleteTxn(txnId);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertEquals(" Given Txn_Id:1is not present In System", exception.getError());

        verify(transactionRepo, times(1)).findById(txnId);
        verify(transactionRepo, never()).deleteById(any());
    }
    
}
