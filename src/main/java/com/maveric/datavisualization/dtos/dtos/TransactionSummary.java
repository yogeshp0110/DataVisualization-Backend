package com.maveric.datavisualization.dtos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    private double totalSpent;
    private String expenseCategory;
    private String mostUsedCard;
    private String bankOfTheMonth;
    private int totalTransactionCount;

    private Map<String, Long> txnsBasedOnStatuses;
}
