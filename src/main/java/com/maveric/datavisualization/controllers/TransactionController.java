package com.maveric.datavisualization.controllers;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maveric.datavisualization.dtos.TransactionDetails;
import com.maveric.datavisualization.dtos.TransactionFilterResponse;
import com.maveric.datavisualization.dtos.TransactionSummary;
import com.maveric.datavisualization.entities.Transaction;
import com.maveric.datavisualization.services.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transaction")
public class TransactionController {


    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getAllTxns")
  	public ResponseEntity<List<Transaction>> getAllTxns() throws JsonProcessingException
  	{
    	logger.info("TransactionController-getAllTxns() call started");
      	List<Transaction> getAllTxnData =transactionService.getAllTxns();
  		if(getAllTxnData==null)
  		{
  			logger.info("TransactionController-getAllTxns() data not present ");
  			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  		}
  		logger.info("TransactionController-getAllTxns() method ended ");
  		return ResponseEntity.status(HttpStatus.CREATED).body(getAllTxnData);
  	}
      /**
       * 
       * @param id
       * @param request
       * @return
       * @throws JsonProcessingException 
       */
      @GetMapping("/getTxnById/{id}")
      public ResponseEntity<Transaction> getTransactions(@PathVariable Long id,HttpServletRequest request) throws JsonProcessingException {
      	 logger.info("TransactionController-getTransactions() call started");
      	logger.info("TransactionController-getTransactions() method ended ");
      	return new ResponseEntity<>(transactionService.getTransactions(id), HttpStatusCode.valueOf(HttpStatus.OK.value()));
      }
     /**
      * 
      * @param id
      * @param transaction
      * @return
      * @throws CustomeException
      */
     @PutMapping("/updateTxn/{id}")
     public ResponseEntity<Transaction> updateTxn(@Valid @PathVariable long id,@Valid @RequestBody TransactionDetails transaction) throws JsonProcessingException {
    	logger.info("TransactionController-updateTxn() method call started");
    	Transaction updateTxndata = transactionService.updateTxn(id, transaction);
    	logger.info("transactionService.updateTxn updateTxndata method {}", updateTxndata);
    	ResponseEntity<Transaction> updateTxnResponse = null;
         if (updateTxndata != null) {
        	 logger.info("updateTxn Data present");
             updateTxnResponse = new ResponseEntity<Transaction>(updateTxndata,HttpStatus.OK);
         	}
         else {
        	 logger.info("updateTxn Data not present");
             updateTxnResponse = ResponseEntity.badRequest().body(null); 
         }
         logger.info("TransactionController-updateTxn() method ended ");
         return updateTxnResponse;
     }
      /**
       * 
       * @param id
       * @return
       * @throws JsonProcessingException 
       */
      @DeleteMapping("/deleteTxn/{id}")
      public ResponseEntity<String> deleteTxn(@PathVariable("id") long id) throws JsonProcessingException 
      {
    	  logger.info("TransactionController-deleteTxn() call started");
    	  transactionService.deleteTxn(id);
    	  logger.info("TransactionController-deleteTxn() call ended");
      	return new ResponseEntity<>("Transaction deleted successfully!", HttpStatus.OK);
      	
      }
    
    
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDetails> createTransaction(@RequestBody @Valid TransactionDetails transactionDetails,
                                                                HttpServletRequest request) {
        logger.debug("TransactionController-createTransaction() call started");
        logger.debug("Transaction Details : {}, Uri : {},", transactionDetails, request.getRequestURI());
        return new ResponseEntity<>(transactionService.createTransaction(transactionDetails), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/summary")
    public ResponseEntity<TransactionSummary> getTransactionSummary(HttpServletRequest request) {
        logger.debug("TransactionController-getTransactionSummary() call started");
        logger.debug("Uri : {}", request.getRequestURI());
        return new ResponseEntity<>(transactionService.getTxnSummaryWithFilterBased(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/filterwise/{bank}/{txnFilter}/{fromDate}/{toDate}")
    public ResponseEntity<TransactionFilterResponse> getTxnSummaryWithFilterBased(@PathVariable String bank,
                                                                                  @PathVariable String txnFilter,
                                                                                  @PathVariable LocalDate fromDate,
                                                                                  @PathVariable LocalDate toDate,
                                                                                  HttpServletRequest request) {
        logger.info("TransactionController-getTxnSummaryWithFilterBased() call started");
        logger.info("bank{},txnFilter{},fromDate{},toDate{},Uri : {}", bank, txnFilter, fromDate, toDate, request.getRequestURI());

        return new ResponseEntity<>(transactionService.getTxnSummaryWithFilterBased(bank, txnFilter, fromDate, toDate), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/institution/wise/{fromDate}/{toDate}")
    public ResponseEntity<TransactionFilterResponse> getSpentsByInstitutionWise(@PathVariable LocalDate fromDate,
                                                                                @PathVariable LocalDate toDate,
                                                                                HttpServletRequest request) {
        logger.debug("TransactionController-getSpentsByInstitutionWise() call started");
        logger.debug("fromDate{},toDate{},Uri : {}", fromDate, toDate, request.getRequestURI());

        return new ResponseEntity<>(transactionService.getSpentsInstitutionWise(fromDate, toDate), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


}
