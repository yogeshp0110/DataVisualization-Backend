package com.maveric.datavisualization.services;

import static com.maveric.datavisualization.dtos.TransactionDetails.FILTER_TXN_BY_CARD;
import static com.maveric.datavisualization.dtos.TransactionDetails.FILTER_TXN_BY_EXPANSE;
import static com.maveric.datavisualization.dtos.TransactionDetails.FILTER_TXN_BY_GENDER;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maveric.datavisualization.daos.TransactionRepo;
import com.maveric.datavisualization.dtos.TransactionDetails;
import com.maveric.datavisualization.dtos.TransactionFilterResponse;
import com.maveric.datavisualization.dtos.TransactionSummary;
import com.maveric.datavisualization.entities.Transaction;
import com.maveric.datavisualization.exception.CustomExceptions;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired

    private TransactionRepo transactionRepo;

    @Autowired
    private ModelMapper modelMapper;
    LocalDate updatedTime = LocalDate.now();
	private final ObjectMapper mapper;

	public TransactionServiceImpl() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
    
	public List<Transaction> getAllTxns()throws JsonProcessingException
	{
		List<Transaction> list=transactionRepo.findAll();
		return list;
	}
	

@Override
public Transaction getTransactions(Long txn_Id) throws JsonProcessingException {
	 logger.info("TransactionServiceImpl-getTransactions() call started");
	 Optional<Transaction> getTxn = transactionRepo.findById(txn_Id);
	 if (getTxn.isEmpty()) {
		 logger.error("TransactionServiceImpl-getTransactions()::: Transactions details not found  with id of -{}",mapper.writeValueAsString(getTxn));
		 throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "Transaction details not found", "Please check Transaction Id", null);
		//throw new CustomeExceptions(HttpStatus.BAD_REQUEST.value(), "Transaction details not found ","Please check Transaction Id", null, updatedTime);
	}
	Transaction userTxns = getTxn.get();
	 logger.info("TransactionServiceImpl-insertTxn():::: data saved in DB{}", mapper.writeValueAsString(userTxns));
	 logger.info("TransactionServiceImpl-getTransactions() call Ended");
	 return userTxns;
}
@Override
public Transaction updateTxn(long txn_Id, TransactionDetails transaction) throws JsonProcessingException {
	logger.info("TransactionServiceImpl-updateTxn() call Started");
	Transaction updatedTxnData = null;
	Optional<Transaction> txnOptional = transactionRepo.findById(txn_Id);
	if (txnOptional.isPresent() && (txn_Id == transaction.getTxnId())) {
		logger.info("In TransactionServiceImpl if txnOptional.isPresent() : {}", mapper.writeValueAsString(txnOptional));
		Transaction existingTransaction = txnOptional.get();
		try {
			logger.info("Existing TxnData From DB before Update: {}", mapper.writeValueAsString(existingTransaction));
			existingTransaction.setCardType(transaction.getCardType());
			existingTransaction.setAmount(transaction.getAmount());
			existingTransaction.setExpType(transaction.getExpType());
			existingTransaction.setBank(transaction.getBank());
			existingTransaction.setCity(transaction.getCity());
			existingTransaction.setGender(transaction.getGender());
			existingTransaction.setStatus(transaction.getStatus());
			existingTransaction.setTxnTime(existingTransaction.getTxnTime());
			existingTransaction.setUpdatedTime(updatedTime);
			updatedTxnData = transactionRepo.save(existingTransaction);
			logger.info("updated TxnData from DB : {}", mapper.writeValueAsString(updatedTxnData));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error occure while updating the Transaction  record in database : {}");
				throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while updating transaction", "An error occurred while updating the transaction", null);
				}
	} else {
		logger.info(" Given Txn_Id: {} in Request and txnId in requestData is not same :::: ", txn_Id,transaction.getTxnId());
		String message = " Given Txn_Id:" + txn_Id + "in Request and txnId in requestData ::: " + transaction.getTxnId()+ "is not same";
		 throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), message, "Please check Transaction Id", null);
		}
	
	return updatedTxnData;
}

@Override
public void deleteTxn(long txn_Id) throws JsonProcessingException {
	try {
		logger.info("TransactionServiceImpl-deleteTxn() call Started");
		Optional<Transaction> deleteTxnData = transactionRepo.findById(txn_Id);
		logger.info("TransactionServiceImpl-deleteTxn(): data fetch from txnOptional{}", mapper.writeValueAsString(deleteTxnData));
		if (deleteTxnData.isPresent()) {
			logger.info("In TransactionServiceImpl deleteTxnData -isPresent() ::: {}", mapper.writeValueAsString(deleteTxnData));
			transactionRepo.deleteById(txn_Id);
		} else {
			
			String message = " Given Txn_Id:" + txn_Id +  "is not present In System";
			 throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), message, "Please check Transaction Id", null);
		}
		
	} catch (Exception e) {
		
		e.printStackTrace();
		
		logger.info("TransactionServiceImpl-deleteTxn() call Ended");
		
		throw e;
	}
}

    public TransactionDetails createTransaction(TransactionDetails transactionDetails) {
        try {
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDetails, transaction);
            try {
                transaction = transactionRepo.save(transaction);

                logger.debug("TransactionServiceImpl-createTransaction() data saved in DB{}", transaction);
                logger.debug("TransactionServiceImpl-createTransaction() call ended");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.error("TransactionServiceImpl-createTransaction() -Failed while saving  Transaction Data in Database");
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while saving Transaction Data in  Database", null, null);
            }
            return modelMapper.map(transaction, TransactionDetails.class);
        } catch (CustomExceptions c) {
            c.printStackTrace();
            logger.error("TransactionServiceImpl-createTransaction() Failed {} -", c.getError());
            throw new CustomExceptions(c.getStatus(), c.getError(), "", null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("TransactionServiceImpl-createTransaction() Failed {} -", throwable.getMessage());
            throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while processing the request", throwable.getMessage(), null);

        }


    }

    @Override
    public TransactionSummary getTxnSummaryWithFilterBased() {
        try {
            LocalDate fromDate = LocalDate.now().minusMonths(1);
            LocalDate toDate = LocalDate.now().minusDays(1);
            List<Transaction> transactions = null;
            try {
                transactions = transactionRepo.findTxnsWithTimeRange(fromDate, toDate);
            } catch (Throwable throwable) {
                logger.error("TransactionServiceImpl-getTransactionSummary() -Failed while getting Transaction Data From Database");
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while getting Transaction Data From Database", "", null);
            }
            logger.info("TransactionServiceImpl-getTransactionSummary()-findTransactionsWithTimeRange() {}", transactions);
            if (CollectionUtils.isEmpty(transactions)) {
                logger.error("TransactionServiceImpl-getTransactionSummary() - No Transaction Data Found {}", transactions);
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.format("No Transaction Data Found in Provided Date Range %s %s", String.valueOf(fromDate), String.valueOf(toDate)), null, null);
            }
            TransactionSummary transactionSummary = new TransactionSummary();
            double totalSpent = transactions.stream().mapToDouble(Transaction::getAmount)
                    .sum();
            logger.info("TransactionServiceImpl-getTransactionSummary()-totalSpent{}", totalSpent);
            transactionSummary.setTotalSpent(totalSpent);

            Optional<String> expenseCategory = transactions.stream()
                    .max(Comparator.comparingDouble(Transaction::getAmount))
                    .map(Transaction::getExpType);
            logger.info("TransactionServiceImpl-getTransactionSummary()-expenseCategory{}", expenseCategory);
            expenseCategory.ifPresent(transactionSummary::setExpenseCategory);
            Map<String, Double> bankWiseTxnData = filterTxnsByBank(transactions);
            Optional<Map.Entry<String, Double>> highestTxnBankData = bankWiseTxnData.entrySet()
                    .stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue));
            logger.info("TransactionServiceImpl-getTransactionSummary()-highestTxnBankData{}", highestTxnBankData);
            highestTxnBankData.ifPresent(stringDoubleEntry -> transactionSummary.setBankOfTheMonth(stringDoubleEntry.getKey()));
            Map<String, Double> filterTransactionsByCard = filterTxnsByCard(transactions);
            logger.info("TransactionServiceImpl-getTransactionSummary()-filterTransactionsByCard{}", filterTransactionsByCard);

            Optional<Map.Entry<String, Double>> highestTxnCardDetails = filterTransactionsByCard.entrySet()
                    .stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue));
            logger.info("TransactionServiceImpl-getTransactionSummary()-highestTxnCardDetails{}", highestTxnCardDetails);
            highestTxnCardDetails.ifPresent(stringDoubleEntry -> transactionSummary.setMostUsedCard(stringDoubleEntry.getKey()));

            Map<String, Double> filterTransactionsByExpense = filterTxnsByExpense(transactions);
            logger.info("TransactionServiceImpl-getTransactionSummary()-filterTransactionsByExpense{}", filterTransactionsByExpense);

            Optional<Map.Entry<String, Double>> highestExpanseCategory = filterTransactionsByExpense.entrySet()
                    .stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue));
            logger.info("TransactionServiceImpl-getTransactionSummary()-highestExpanseCategory{}", highestExpanseCategory);
            highestExpanseCategory.ifPresent(stringDoubleEntry -> transactionSummary.setExpenseCategory(stringDoubleEntry.getKey()));
            Map<String, Long> txnDataBasedOnStatuses = transactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getStatus, Collectors.counting()));
            logger.info("TransactionServiceImpl-getTransactionSummary()-txnDataBasedOnStatuses{}", txnDataBasedOnStatuses);
            transactionSummary.setTxnsBasedOnStatuses(txnDataBasedOnStatuses);
            logger.info("TransactionServiceImpl-getTransactionSummary()-totalTransactionCount{}", transactions.size());
            transactionSummary.setTotalTransactionCount(transactions.size());
            logger.info("TransactionServiceImpl-getTransactionSummary()-transactionSummary {}", transactionSummary);
            logger.info("TransactionServiceImpl-getTransactionSummary() call ended");
            return transactionSummary;
        } catch (CustomExceptions c) {
            c.printStackTrace();
            logger.error("TransactionServiceImpl-getTransactionSummary() Failed {} -", c.getError());
            throw new CustomExceptions(c.getStatus(), c.getError(), "", null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("TransactionServiceImpl-getTransactionSummary() Failed {} -", throwable.getMessage());
            throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while processing the request", throwable.getMessage(), null);
        }

    }


    @Override
    public TransactionFilterResponse getTxnSummaryWithFilterBased(String bank, String transactionFilter, LocalDate fromDate, LocalDate toDate) {
        try {
            List<Transaction> transactionList = null;
            try {
                transactionList = transactionRepo.findTxnsByBankWithTimeRange(bank, fromDate, toDate);
            } catch (Throwable throwable) {
                logger.error("TransactionServiceImpl-getTxnSummaryWithFilterBased() -Failed while getting Transaction Data From Database");
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while getting Transaction Data From Database", "", null);
            }
            logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased()-findTxnsByBankWithTimeRange {}", transactionList);
            if (CollectionUtils.isEmpty(transactionList)) {
                logger.error("TransactionServiceImpl -getTxnSummaryWithFilterBased - No Transaction Found {}", transactionList);
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.format("No Transaction Found in Provided Date Range %s %s", String.valueOf(fromDate), String.valueOf(toDate)), null, null);
            }
            Map<String, Double> filterTransactionsData = new HashMap<>();
            if (transactionFilter.equalsIgnoreCase(FILTER_TXN_BY_CARD)) {
                filterTransactionsData = filterTxnsByCard(transactionList);
                logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased()-filterTxnsByCard {}", filterTransactionsData);
            } else if (transactionFilter.equalsIgnoreCase(FILTER_TXN_BY_GENDER)) {

                filterTransactionsData = filterTxnsByGender(transactionList);
                logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased()-filterTxnsByGender {}", filterTransactionsData);

            } else if (transactionFilter.equalsIgnoreCase(FILTER_TXN_BY_EXPANSE)) {
                filterTransactionsData = filterTxnsByExpense(transactionList);
                logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased()-filterTxnByExpense {}", filterTransactionsData);
            }
            TransactionFilterResponse transactionFilterResponse = new TransactionFilterResponse();
            transactionFilterResponse.setFilterTransactions(filterTransactionsData);
            logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased() transactionFilterResponse {}", transactionFilterResponse);
            logger.info("TransactionServiceImpl-getTxnSummaryWithFilterBased() call ended");
            return transactionFilterResponse;
        } catch (CustomExceptions c) {
            c.printStackTrace();
            logger.error("TransactionServiceImpl-getTxnSummaryWithFilterBased() Failed {} -", c.getError());
            throw new CustomExceptions(c.getStatus(), c.getError(), "", null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("TransactionServiceImpl-getTxnSummaryWithFilterBased() Failed {}", throwable.getMessage());
            throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while processing the request", "", null);
        }
    }

    @Override
    public TransactionFilterResponse getSpentsInstitutionWise(LocalDate fromDate, LocalDate toDate) {
        try {
            List<Transaction> transactionList = null;
            try {
                transactionList = transactionRepo.findTxnsWithTimeRange(fromDate, toDate);
            } catch (Throwable throwable) {
                logger.error("TransactionServiceImpl-getSpentsInstitutionWise() -Failed while getting Transaction Data From Database");
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while getting Transaction Data From Database", "", null);
            }
            logger.info("TransactionServiceImpl-getSpentsInstitutionWise()-transactionList {}", transactionList);
            if (CollectionUtils.isEmpty(transactionList)) {
                logger.error("TransactionServiceImpl-getSpentsInstitutionWise() - No Transaction Data Found {}", transactionList);
                throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.format("No Transaction Found in Provided Date Range %s %s", String.valueOf(fromDate), String.valueOf(toDate)), null, null);
            }
            Map<String, Double> filterTransactionsByBank = filterTxnsByBank(transactionList);
            TransactionFilterResponse transactionFilterResponse = new TransactionFilterResponse();
            transactionFilterResponse.setFilterTransactions(filterTransactionsByBank);
            logger.info("TransactionServiceImpl-getSpentsInstitutionWise()-transactionSummary {}", transactionFilterResponse);
            logger.info("TransactionServiceImpl-getSpentsInstitutionWise() call ended");
            return transactionFilterResponse;
        } catch (CustomExceptions c) {
            c.printStackTrace();
            logger.error("TransactionServiceImpl-getSpentsInstitutionWise() Failed {} -", c.getError());
            throw new CustomExceptions(c.getStatus(), c.getError(), "", null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("TransactionServiceImpl-getSpentsInstitutionWise() Failed {} -", throwable.getMessage());
            throw new CustomExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception Occurs while processing the request", throwable.getMessage(), null);
        }
    }


    public List<TransactionDetails> convertTransactionsToTransactionDetails(List<Transaction> transactions) {

        return transactions.stream()
                .map(product -> modelMapper.map(product, TransactionDetails.class))
                .collect(Collectors.toList());

    }


    Map<String, Double> filterTxnsByBank(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getBank,
                        Collectors.summingDouble(Transaction::getAmount)));
    }

    Map<String, Double> filterTxnsByExpense(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getExpType,

                        Collectors.summingDouble(Transaction::getAmount)));
    }

    Map<String, Double> filterTxnsByCard(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCardType,
                        Collectors.summingDouble(Transaction::getAmount)));
    }

    Map<String, Double> filterTxnsByGender(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getGender,
                        Collectors.summingDouble(Transaction::getAmount)));
    }


}
