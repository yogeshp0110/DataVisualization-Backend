package com.maveric.datavisualization.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maveric.datavisualization.dtos.TransactionDetails;
import com.maveric.datavisualization.dtos.TransactionFilterResponse;
import com.maveric.datavisualization.dtos.TransactionSummary;
import com.maveric.datavisualization.entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    public TransactionDetails createTransaction(TransactionDetails transactionDetails);


    TransactionSummary getTxnSummaryWithFilterBased();
    public TransactionFilterResponse getTxnSummaryWithFilterBased(String bank, String filterContext, LocalDate fromDate, LocalDate toDate);

    public TransactionFilterResponse getSpentsInstitutionWise(LocalDate fromDate, LocalDate toDate);
    public Transaction getTransactions(Long id)throws JsonProcessingException;
    Transaction updateTxn(long id, TransactionDetails transaction) throws JsonProcessingException;
  	void deleteTxn(long id) throws JsonProcessingException;
  	public List<Transaction> getAllTxns()throws JsonProcessingException;

}
