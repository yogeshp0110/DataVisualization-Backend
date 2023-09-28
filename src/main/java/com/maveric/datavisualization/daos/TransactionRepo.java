package com.maveric.datavisualization.daos;

import com.maveric.datavisualization.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {


    @Query("from Transaction where txnTime BETWEEN :fromDate AND :toDate")
    List<Transaction> findTxnsWithTimeRange(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    @Query("from Transaction where bank=:bank and txnTime BETWEEN :fromDate AND :toDate")
    List<Transaction> findTxnsByBankWithTimeRange(@Param("bank") String bank,
                                                  @Param("fromDate") LocalDate fromDate,
                                                  @Param("toDate") LocalDate toDate);
}
